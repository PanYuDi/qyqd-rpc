package github.qyqd.rpc.proxy;

import github.qyqd.rpc.invoker.Invocation;

/**
 * @Author: 潘语笛
 * @Date: 25/12/2021 下午8:05
 * @Description: 代理类创建
 */
public interface ProxyFactory {
    /**
     * 创建一个代理类
     * @param invocation
     * @return
     */
    public Object createProxy(Invocation invocation);
}
