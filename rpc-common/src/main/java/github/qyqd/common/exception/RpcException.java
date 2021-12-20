package github.qyqd.common.exception;

/**
 * @ClassName RpcException
 * @Description RPC调用相关的异常
 * @Author 潘语笛
 * @Date 20/12/2021 下午2:17
 * Version 1.0
 */
public class RpcException extends RuntimeException {
    public RpcException(String msg) {
        super(msg);
    }
}
