package github.qyqd.common.exception;

/**
 * @ClassName ProtocolException
 * @Description 数据传输异常
 * @Author 潘语笛
 * @Date 25/11/2021 下午2:18
 * Version 1.0
 */
public class ProtocolException extends RuntimeException{
    public ProtocolException(String msg) {
        super(msg);
    }
}
