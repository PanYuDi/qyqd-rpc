package github.qyqd.remote.transport.netty.handler.factory;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.remote.transport.netty.handler.HeartbeatRequestMessageHandler;
import github.qyqd.remote.transport.netty.handler.MessageHandlerContext;
import github.qyqd.remote.transport.netty.MessageHandler;

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
        messageHandlerContext.put(ProtocolMessageTypeEnum.HEARTBEAT_REQUEST_MESSAGE, new HeartbeatRequestMessageHandler());
        return messageHandlerContext;
    }
}
