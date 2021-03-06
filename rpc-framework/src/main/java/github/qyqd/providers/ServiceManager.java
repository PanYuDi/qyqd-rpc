package github.qyqd.providers;

import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.scanner.ServiceInfo;

/**
 * @ClassName ServiceManager
 * @Description 服务管理器,该接口的实例对象一般只有一个
 * @Author 潘语笛
 * @Date 22/12/2021 下午2:06
 * Version 1.0
 */
public interface ServiceManager extends Provider {
    /**
     * 添加一个服务，每个invoker代表一个服务
     * @param invoker
     * @param invocation 服务信息
     */
    public void addService(ServiceInfo serviceInfo, Invoker invoker);

}
