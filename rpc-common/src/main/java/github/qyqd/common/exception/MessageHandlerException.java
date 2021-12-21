package github.qyqd.common.exception;

/**
 * @ClassName MessageHandlerException
 * @Description 消息处理器发生的异常
 * @Author 潘语笛
 * @Date 7/12/2021 下午2:39
 * Version 1.0
 */
public class MessageHandlerException extends RpcException{
    public MessageHandlerException(String msg) {
        super(msg);
    }
}
