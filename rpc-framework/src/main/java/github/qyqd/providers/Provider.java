package github.qyqd.providers;

import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;

/**
 * @ClassName ServiceProvider
 * @Description 服务提供类,相当于Invoker的工厂
 * @Author 潘语笛
 * @Date 21/12/2021 上午11:14
 * Version 1.0
 */
public interface Provider {
    /**
     * 获取执行器
     * @param invocation
     * @return
     */
    Invoker getInvoker(Invocation invocation);
}
