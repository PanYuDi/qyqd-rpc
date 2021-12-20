package github.qyqd.rpc.remote.transport.netty.client;

import github.qyqd.rpc.remote.RequestMessage;
import github.qyqd.rpc.remote.RpcClient;
import github.qyqd.rpc.remote.message.ProtocolMessage;
import github.qyqd.rpc.remote.transport.netty.channel.NettyRpcClientChannelHandler;
import github.qyqd.rpc.remote.transport.netty.codec.ChannelMessageDecoder;
import github.qyqd.rpc.remote.transport.netty.codec.ChannelMessageEncoder;
import github.qyqd.rpc.remote.transport.netty.request.ProtocolRequestEndpointWrapper;
import github.qyqd.rpc.remote.transport.serialize.ProtostuffSerializer;
import github.qyqd.rpc.remote.transport.serialize.Serializer;
import github.qyqd.rpc.remote.utils.ProtocolMessageUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    public NettyClient(Long timeout) {
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
                        pipeline.addLast(new NettyRpcClientChannelHandler());
                    }
                });
        unprocessedRequest = UnprocessedRequest.getSingleton(timeout);
    }
    @Override
    public Object send(ProtocolRequestEndpointWrapper req) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(req.getHost(), req.getPort());
        ProtocolMessage protocolMessage = ProtocolMessageUtils.buildProtocolMessage(req.getRequestBody(), req.getMessageTypeEnum());
        CompletableFuture<RequestMessage> resultFuture = new CompletableFuture<>();
        try {
            ChannelFuture channelFuture = bootstrap.connect(req.getHost(), req.getPort()).sync();
            Channel channel = channelFuture.channel();
            channelFuture.addListener((ChannelFutureListener)future->{
                if(future.isSuccess()) {
                    log.info("The client has connected [{}] successful!", inetSocketAddress.toString());
                } else {
                    throw new IllegalStateException();
                }
            });
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return resultFuture;
    }
}
