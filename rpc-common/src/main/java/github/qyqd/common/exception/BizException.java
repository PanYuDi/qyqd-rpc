package github.qyqd.common.exception;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: 潘语笛
 * @Date: 21/12/2021 下午9:18
 * @Description: Rpc调用时遭遇的异常
 */
@Getter
public class BizException extends Exception {
    Throwable t;
    String msg;
    public BizException(Throwable t, String msg) {
        this.msg = msg;
        this.t = t;
    }
}
