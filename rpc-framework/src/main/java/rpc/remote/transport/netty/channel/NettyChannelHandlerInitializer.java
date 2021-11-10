package rpc.remote.transport.netty.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import rpc.remote.MessageHandler;

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

    }
}
