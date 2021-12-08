package github.qyqd.rpc.remote.transport.netty.handler;

import github.qyqd.rpc.remote.RequestMessage;
import github.qyqd.rpc.remote.transport.netty.MessageHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName HeartbeatResponseMessageHandler
 * @Description 心跳返回值处理器
 * @Author 潘语笛
 * @Date 8/12/2021 上午11:29
 * Version 1.0
 */
public class HeartbeatResponseMessageHandler implements MessageHandler {
    @Override
    public Object handle(RequestMessage message, ChannelHandlerContext ctx) {
        return null;
    }

    @Override
    public boolean canHandle(Object message) {
        return false;
    }
}
