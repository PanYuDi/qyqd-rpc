package github.qyqd.rpc.remote.transport.netty.handler;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.common.exception.ProtocolException;
import github.qyqd.rpc.remote.RequestMessage;
import github.qyqd.rpc.remote.message.ProtocolMessage;
import github.qyqd.rpc.remote.transport.netty.MessageHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MessageHandlerContext
 * @Description 根消息处理器
 * @Author 潘语笛
 * @Date 25/11/2021 上午11:33
 * Version 1.0
 */
@Slf4j
public class MessageHandlerContext implements MessageHandler {
    Map<ProtocolMessageTypeEnum, MessageHandler> handlerMap = new ConcurrentHashMap<>();
    @Override
    public Object handle(RequestMessage message, ChannelHandlerContext ctx) {
        ProtocolMessage protocolMessage = (ProtocolMessage) message;
        if(protocolMessage.getMessageType() == null) {
            throw new ProtocolException("message type un support!!!");
        }

        return handlerMap.get(protocolMessage).handle(message, ctx);
    }

    @Override
    public boolean canHandle(Object message) {
        if(message instanceof ProtocolMessage) {
            return true;
        } else {
            return false;
        }
    }
}
