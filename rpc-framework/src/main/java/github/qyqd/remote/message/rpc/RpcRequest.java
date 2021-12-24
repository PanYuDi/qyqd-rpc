package github.qyqd.remote.message.rpc;

import github.qyqd.remote.RequestMessage;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName RpcRequest
 * @Description RPC调用请求消息体
 * @Author 潘语笛
 * @Date 21/12/2021 上午10:33
 * Version 1.0
 */
@Data
public class RpcRequest implements RequestMessage, Serializable {
    private static final Long serializationId = 2L;
    String methodName;
    String interfaceName;
    String serviceName;
    Integer requestId;
    Object[] parameters;
    Class<?>[] parameterTypes;
}
