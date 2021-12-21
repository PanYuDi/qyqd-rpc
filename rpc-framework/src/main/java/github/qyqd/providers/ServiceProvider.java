package github.qyqd.providers;

import github.qyqd.rpc.invoker.Invoker;

/**
 * @ClassName ServiceProvider
 * @Description 服务提供类
 * @Author 潘语笛
 * @Date 21/12/2021 上午11:14
 * Version 1.0
 */
public interface ServiceProvider {
    /**
     * 添加一个调用服务
     * @param route 服务路由
     */
    void addService(String route);

    /**
     * 获取一个服务的启动器
     * @param route 服务路由
     * @return
     */
    Invoker getService(String route);
}
