package github.qyqd.remote.message.rpc;

import github.qyqd.common.enums.ResponseType;
import github.qyqd.common.exception.BizException;
import github.qyqd.common.exception.RpcException;
import github.qyqd.remote.RequestMessage;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName RpcResponse
 * @Description Rpc返回值消息
 * @Author 潘语笛
 * @Date 24/12/2021 下午1:47
 * Version 1.0
 */
@Data
public class RpcResponse<T> implements RequestMessage, Serializable {
    private static final long serializationId = 3L;
    /**
     * 请求id
     */
    Integer requestId;
    /**
     * 返回状态
     */
    Integer status;
    /**
     * 结果值
     */
    T result;
    /**
     * 异常
     */
    Throwable t;
    /**
     * 额外信息
     */
    String msg;

    public static <T> RpcResponse<T> buildSuccessResult(T result, Integer requestId) {
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setResult(result);
        rpcResponse.setRequestId(requestId);
        rpcResponse.setStatus(ResponseType.SUCCESS.getCode());
        return rpcResponse;
    }

    public static RpcResponse buildFailedResult(Throwable t, Integer requestId) {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setT(t);
        rpcResponse.setRequestId(requestId);
        if(t instanceof RpcException) {
            rpcResponse.setStatus(ResponseType.RPC_FAILED.getCode());
        } else if(t instanceof BizException) {
            rpcResponse.setStatus(ResponseType.BIZ_FAILED.getCode());
        } else {
            rpcResponse.setStatus(ResponseType.FAILED.getCode());
        }
        return rpcResponse;
    }
}
