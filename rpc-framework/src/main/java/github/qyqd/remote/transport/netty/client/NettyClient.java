package github.qyqd.remote.transport.netty.client;

import github.qyqd.common.exception.RpcException;
import github.qyqd.remote.RequestMessage;
import github.qyqd.remote.RpcClient;
import github.qyqd.remote.constant.ProtocolConstant;
import github.qyqd.remote.message.ProtocolMessage;
import github.qyqd.remote.transport.netty.channel.NettyRpcClientChannelHandler;
import github.qyqd.remote.transport.netty.codec.ChannelMessageDecoder;
import github.qyqd.remote.transport.netty.codec.ChannelMessageEncoder;
import github.qyqd.remote.transport.netty.request.ProtocolRequestEndpointWrapper;
import github.qyqd.remote.utils.ProtocolMessageUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName NettyClient
 * @Description 数据通信客户端
 * @Author 潘语笛
 * @Date 29/11/2021 上午11:04
 * Version 1.0
 */
@Slf4j
public class NettyClient implements RpcClient {
    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;
    private UnprocessedRequest unprocessedRequest;
    NettyChannelContext nettyChannelContext =  new NettyChannelContext();
    public NettyClient() {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ChannelMessageEncoder());
                        pipeline.addLast(new ChannelMessageDecoder());
                        pipeline.addLast(new NettyRpcClientChannelHandler(nettyChannelContext));
                        pipeline.addLast(new IdleStateHandler(ProtocolConstant.READ_IDLE_TIME_SECOND, ProtocolConstant.WRITE_IDLE_TIME_SECOND, ProtocolConstant.ALL_IDLE_TIME_SECOND));

                    }
                });
        unprocessedRequest = UnprocessedRequest.getSingleton();
    }
    @Override
    public Object send(ProtocolRequestEndpointWrapper req) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(req.getHost(), req.getPort());
        ProtocolMessage protocolMessage = ProtocolMessageUtils.buildProtocolMessage(req.getRequestBody(), req.getMessageTypeEnum());
        CompletableFuture<RequestMessage> resultFuture = new CompletableFuture<>();
        Channel channel = getChannel(inetSocketAddress);
        if(channel.isActive()) {
            // 放入结果future
            unprocessedRequest.putUnprocessedRequest(req.getRequestId(), resultFuture);
            channel.writeAndFlush(protocolMessage).addListener((ChannelFutureListener) future ->{
                if(future.isSuccess()) {
                    log.info("client send message : [{}]", protocolMessage);
                } else {
                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                    log.error("Send failed:", future.cause());
                }
            });
        } else {
            throw new IllegalStateException();
        }
        return resultFuture;
    }

    private Channel getChannel(InetSocketAddress inetSocketAddress) {
        // TODO 获取Channel
        Channel channel = nettyChannelContext.get(inetSocketAddress);
        // 缓存未命中
        if(channel == null) {
            ChannelFuture channelFuture = null;
            try {
                channelFuture = bootstrap.connect(inetSocketAddress).sync();
                channel = channelFuture.channel();
                if(channel.isActive()) {
                    // 放入缓存并返回
                    nettyChannelContext.put(inetSocketAddress, channel);
                    return channel;
                } else {
                    throw new RpcException("connect rpc server failed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RpcException("connect rpc server failed");
            }
        } else {
            return channel;
        }
    }
}
