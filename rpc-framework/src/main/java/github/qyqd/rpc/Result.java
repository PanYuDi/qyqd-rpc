package github.qyqd.rpc;

/**
 * @ClassName Result
 * @Description 返回结果
 * @Author 潘语笛
 * @Date 21/12/2021 下午1:29
 * Version 1.0
 */
public interface Result {
    /**
     * 获取结果值
     * @return
     */
    Object getValue();

    /**
     * 获取返回的异常
     * @return
     */
    Throwable getException();

    /**
     * 设置异常
     * @param t
     */
    void setException(Throwable t);
}
