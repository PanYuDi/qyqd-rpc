package github.qyqd.rpc.remote.transport.netty.handler;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.rpc.remote.RequestMessage;
import github.qyqd.rpc.remote.message.ProtocolMessage;
import github.qyqd.rpc.remote.message.heartbeat.HeartbeatMessage;
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
        System.out.println(((HeartbeatMessage)message).getContent());
        return null;
    }

    @Override
    public boolean canHandle(Object message) {
        if(message instanceof HeartbeatMessage) {
            return true;
        }
        return false;
    }

    @Override
    public Class getHandleableType() {
        return HeartbeatMessage.class;
    }
}
