package github.qyqd.rpc.remote.utils;

import github.qyqd.common.enums.ProtocolMessageTypeEnum;
import github.qyqd.rpc.remote.RequestMessage;
import github.qyqd.rpc.remote.constant.ProtocolConstant;
import github.qyqd.rpc.remote.message.ProtocolMessage;
import github.qyqd.rpc.remote.message.heartbeat.HeartbeatMessage;
import github.qyqd.rpc.remote.transport.serialize.ProtostuffSerializer;
import github.qyqd.rpc.remote.transport.serialize.Serializer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ProtocolMessageUtils
 * @Description 协议消息相关工具类
 * @Author 潘语笛
 * @Date 9/12/2021 下午3:27
 * Version 1.0
 */
public class ProtocolMessageUtils {
    private static ThreadLocal<Integer> currentRequestId = new ThreadLocal<>();
    private static AtomicInteger requestIdGenerator = new AtomicInteger(0);
    static Serializer serializer = new ProtostuffSerializer();
    public static HeartbeatMessage createHeartBeatRequestMessage() {
        return new HeartbeatMessage(ProtocolConstant.HEARTBEAT_REQUEST_PAYLOAD);
    }

    public static HeartbeatMessage createHeartBeatResponseMessage() {
        return new HeartbeatMessage(ProtocolConstant.HEARTBEAT_RESPONSE_PAYLOAD);
    }
    public static ProtocolMessage buildProtocolMessage(RequestMessage requestMessage, ProtocolMessageTypeEnum typeEnum) {
        ProtocolMessage protocolMessage = ProtocolMessage.builder().messageType(typeEnum).requestId(0).content(serializer.serialize(requestMessage)).build();
        protocolMessage.setLen(protocolMessage.getContent().length);
        protocolMessage.setRequestId(currentRequestId.get());
        return protocolMessage;

    }
    public static RequestMessage deserializeRequestMessage(ProtocolMessage protocolMessage, Class clazz) {
        return (RequestMessage) serializer.deSerialize(protocolMessage.getContent(), clazz);
    }
    public static Integer generateRequestId() {
        int requestId = requestIdGenerator.getAndIncrement();
        currentRequestId.set(requestId);
        return requestId;
    }
    public static void setRequestId(Integer requestId) {
        currentRequestId.set(requestId);
    }
    public static Integer getRequestId() {
        return currentRequestId.get();
    }
}
