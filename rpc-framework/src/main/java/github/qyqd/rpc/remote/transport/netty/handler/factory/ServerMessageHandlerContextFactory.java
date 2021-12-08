package github.qyqd.rpc.remote.transport.netty.handler.factory;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.rpc.remote.transport.netty.MessageHandler;
import github.qyqd.rpc.remote.transport.netty.handler.HeartbeatMessageHandler;
import github.qyqd.rpc.remote.transport.netty.handler.MessageHandlerContext;

/**
 * @ClassName NettyServerMessageHandlerContextFactory
 * @Description 服务端消息处理器工厂
 * @Author 潘语笛
 * @Date 8/12/2021 上午11:13
 * Version 1.0
 */
public class ServerMessageHandlerContextFactory implements MessageHandlerFactory {
    @Override
    public MessageHandler create() {
        MessageHandlerContext messageHandlerContext = new MessageHandlerContext();
        messageHandlerContext.put(ProtocolMessageTypeEnum.HEARTBEAT_REQUEST_MESSAGE, new HeartbeatMessageHandler());
        return messageHandlerContext;
    }
}
