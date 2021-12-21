package github.qyqd.remote.message.heartbeat;

import github.qyqd.remote.RequestMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName HeartbeatMessage
 * @Description 心跳消息
 * @Author 潘语笛
 * @Date 9/12/2021 下午5:01
 * Version 1.0
 */
@AllArgsConstructor
@Data
public class HeartbeatMessage implements RequestMessage, Serializable {
    private static final long serializationId = 1L;
    byte[] content;
}
