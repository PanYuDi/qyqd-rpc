package github.qyqd.common.exception;

import org.omg.SendingContext.RunTime;

/**
 * @ClassName TimeOutException
 * @Description RPC超时异常
 * @Author 潘语笛
 * @Date 20/12/2021 下午2:52
 * Version 1.0
 */
public class TimeOutException extends RpcException {
    public TimeOutException(String msg) {
        super(msg);
    }
}
