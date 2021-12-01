package github.qyqd.rpc.remote.transport.netty.channel;

import github.qyqd.rpc.remote.transport.netty.codec.ChannelMessageDecoder;
import github.qyqd.rpc.remote.transport.netty.codec.ChannelMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import github.qyqd.rpc.remote.transport.netty.MessageHandler;

import java.util.List;

/**
 * @ClassName RpcChannelHandlerInitializer
 * @Description TODO
 * @Author 潘语笛
 * @Date 2021/11/9 11:28
 * Version 1.0
 */
public class NettyChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {
    List<MessageHandler> messageHandlers;
    public NettyChannelHandlerInitializer(List<MessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new ChannelMessageEncoder());
        pipeline.addLast(new ChannelMessageDecoder());
        pipeline.addLast(new NettyRpcServerChannelHandler());
    }
}
