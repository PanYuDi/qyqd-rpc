package github.qyqd.rpc.remote.transport.netty.client;

import github.qyqd.rpc.remote.RpcClient;
import github.qyqd.rpc.remote.message.ProtocolMessage;
import github.qyqd.rpc.remote.transport.netty.channel.NettyRpcClientChannelHandler;
import github.qyqd.rpc.remote.transport.netty.codec.ChannelMessageDecoder;
import github.qyqd.rpc.remote.transport.netty.codec.ChannelMessageEncoder;
import github.qyqd.rpc.remote.transport.netty.request.ProtocolRequest;
import github.qyqd.rpc.remote.transport.netty.request.ProtocolRequestEndpointWrapper;
import github.qyqd.rpc.remote.transport.serialize.ProtostuffSerializer;
import github.qyqd.rpc.remote.transport.serialize.Serializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @ClassName NettyClient
 * @Description 数据通信客户端
 * @Author 潘语笛
 * @Date 29/11/2021 上午11:04
 * Version 1.0
 */
public class NettyClient implements RpcClient {
    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;
    private final Serializer serializer = new ProtostuffSerializer();
    public NettyClient() {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ChannelMessageEncoder());
                        pipeline.addLast(new ChannelMessageDecoder());
                        pipeline.addLast(new NettyRpcClientChannelHandler());
                    }
                });
    }
    @Override
    public void send(ProtocolRequestEndpointWrapper req) {
        ProtocolMessage protocolMessage = ProtocolMessage.builder()
                .messageType(req.getMessageTypeEnum())
                .content(serializer.serialize(req.getRequestBody())).build();
        protocolMessage.setLen(protocolMessage.getContent().length);
        try {
            ChannelFuture channelFuture = bootstrap.connect(req.getHost(), req.getPort()).sync();
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(protocolMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
