package github.qyqd.common.exception;

/**
 * @ClassName MessageDecodeException
 * @Description 编码解码异常
 * @Author 潘语笛
 * @Date 24/11/2021 下午2:16
 * Version 1.0
 */
public class MessageCodecException extends RuntimeException {
    public MessageCodecException(String msg) {
        super(msg);
    }
}
