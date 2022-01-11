package github.qyqd.remote.transport.netty.handler;

import github.qyqd.common.exception.RpcException;
import github.qyqd.remote.RequestMessage;
import github.qyqd.remote.message.ProtocolMessage;
import github.qyqd.remote.message.heartbeat.HeartbeatMessage;
import github.qyqd.remote.message.rpc.RpcResponse;
import github.qyqd.remote.transport.netty.MessageHandler;
import github.qyqd.remote.transport.netty.client.UnprocessedRequest;
import github.qyqd.remote.transport.serialize.ProtostuffSerializer;
import github.qyqd.remote.transport.serialize.Serializer;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ResponseDeserilizeHandler
 * @Description 判定返回值类型并放入UnprocessedRequest
 * @Author 潘语笛
 * @Date 27/12/2021 上午10:47
 * Version 1.0
 */
@Slf4j
public class ResponseDeserilizeHandler implements MessageHandler {
    Serializer serializer = new ProtostuffSerializer();
    UnprocessedRequest unprocessedRequest = UnprocessedRequest.getSingleton();
    @Override
    public Object handle(RequestMessage message, ChannelHandlerContext ctx) {
        RequestMessage result = null;
        byte[] content = ((ProtocolMessage) message).getContent();
        Integer requestId = ((ProtocolMessage) message).getRequestId();
        if(!unprocessedRequest.contains(requestId)) {
            return null;
        }
        switch (((ProtocolMessage)message).getMessageType()) {
            case RPC_RESPONSE:
                result = serializer.deSerialize(content, RpcResponse.class);
                break;
            case HEARTBEAT_RESPONSE_MESSAGE:
                log.debug("接收到心跳返回消息 from {}", ctx.channel().remoteAddress().toString());
                result = serializer.deSerialize(content, HeartbeatMessage.class);
                break;
            default:
                throw new RpcException("response message type not support!");
        }
        unprocessedRequest.complete(requestId, result);
        return result;
    }

    @Override
    public boolean canHandle(Object message) {
        return message instanceof  ProtocolMessage;
    }

    @Override
    public Class getHandleableType() {
        return ProtocolMessage.class;
    }
}
