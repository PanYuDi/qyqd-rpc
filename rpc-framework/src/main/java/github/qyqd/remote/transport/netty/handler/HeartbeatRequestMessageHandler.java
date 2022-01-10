package github.qyqd.remote.transport.netty.handler;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.common.exception.MessageHandlerException;
import github.qyqd.remote.RequestMessage;
import github.qyqd.remote.constant.ProtocolConstant;
import github.qyqd.remote.message.ProtocolMessage;
import github.qyqd.remote.message.heartbeat.HeartbeatMessage;
import github.qyqd.remote.transport.netty.MessageHandler;
import github.qyqd.remote.utils.ProtocolMessageUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @ClassName HeartbeatMessageHandler
 * @Description 心跳检测包处理器
 * @Author 潘语笛
 * @Date 7/12/2021 下午2:35
 * Version 1.0
 */
@Slf4j
public class HeartbeatRequestMessageHandler implements MessageHandler {
    @Override
    public Object handle(RequestMessage message, ChannelHandlerContext ctx) {
        HeartbeatMessage heartbeatMessage = (HeartbeatMessage) message;
        byte[] content = heartbeatMessage.getContent();
        if(!Arrays.equals(content, ProtocolConstant.HEARTBEAT_REQUEST_PAYLOAD)) {
            throw new MessageHandlerException("you gotta be kidding me");
        }
        log.debug("接受到心跳消息{}", new String(content, StandardCharsets.UTF_8));
        ProtocolMessage protocolMessage = ProtocolMessageUtils.buildProtocolMessage(ProtocolMessageUtils.createHeartBeatResponseMessage(), ProtocolMessageTypeEnum.HEARTBEAT_RESPONSE_MESSAGE);
        ctx.writeAndFlush(protocolMessage);
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
