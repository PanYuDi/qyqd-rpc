package github.qyqd.rpc.remote.transport.netty.handler;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.common.exception.MessageHandlerException;
import github.qyqd.rpc.remote.RequestMessage;
import github.qyqd.rpc.remote.constant.ProtocolConstant;
import github.qyqd.rpc.remote.message.ProtocolMessage;
import github.qyqd.rpc.remote.transport.netty.MessageHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;

/**
 * @ClassName HeartbeatMessageHandler
 * @Description 心跳检测包处理器
 * @Author 潘语笛
 * @Date 7/12/2021 下午2:35
 * Version 1.0
 */
public class HeartbeatRequestMessageHandler implements MessageHandler {
    @Override
    public Object handle(RequestMessage message, ChannelHandlerContext ctx) {
        byte[] content = ((ProtocolMessage) message).getContent();
        if(Arrays.equals(content, ProtocolConstant.HEARTBEAT_REQUEST_PAYLOAD)) {
            throw new MessageHandlerException("you gotta be kidding me");
        }
        ctx.writeAndFlush(ProtocolMessage.builder().messageType(ProtocolMessageTypeEnum.HEARTBEAT_RESPONSE_MESSAGE)
            .content(ProtocolConstant.HEARTBEAT_RESPONSE_PAYLOAD)
            .len(ProtocolConstant.HEARTBEAT_RESPONSE_PAYLOAD.length).build());
        return null;
    }

    @Override
    public boolean canHandle(Object message) {
        if(message instanceof ProtocolMessage) {
            if (((ProtocolMessage) message).getMessageType() == ProtocolMessageTypeEnum.HEARTBEAT_REQUEST_MESSAGE) {
                return true;
            }
        }
        return false;
    }
}
