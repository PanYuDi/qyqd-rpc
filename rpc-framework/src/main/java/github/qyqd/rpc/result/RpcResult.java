package github.qyqd.rpc.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Author: 潘语笛
 * @Date: 21/12/2021 下午9:09
 * @Description: Rpc结果
 */
@NoArgsConstructor
@AllArgsConstructor
public class RpcResult implements Result{
    Object value;
    Throwable exception;
    public RpcResult(Object result) {
        this.value = result;
    }
    public RpcResult(Throwable t) {
        exception = t;
    }
    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Throwable getException() {
        return exception;
    }

    @Override
    public void setException(Throwable t) {
        this.exception = t;
    }
}
