package github.qyqd.remote.transport.netty.handler.factory;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.remote.transport.netty.MessageHandler;
import github.qyqd.remote.transport.netty.handler.HeartbeatResponseMessageHandler;
import github.qyqd.remote.transport.netty.handler.MessageHandlerContext;

/**
 * @ClassName ClientMessageHandlerContextFactory
 * @Description 客户端handler工厂类
 * @Author 潘语笛
 * @Date 8/12/2021 下午4:41
 * Version 1.0
 */
public class ClientMessageHandlerContextFactory implements MessageHandlerFactory{
    @Override
    public MessageHandler create() {
        MessageHandlerContext messageHandlerContext = new MessageHandlerContext();
        messageHandlerContext.put(ProtocolMessageTypeEnum.HEARTBEAT_RESPONSE_MESSAGE, new HeartbeatResponseMessageHandler());
        return messageHandlerContext;
    }
}
