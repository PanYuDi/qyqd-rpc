package github.qyqd.registry;

import github.qyqd.providers.ServiceManager;
import github.qyqd.providers.ServiceManagerImpl;
import github.qyqd.rpc.invoker.ServiceInvoker;
import github.qyqd.scanner.ServiceInfo;

/**
 * @ClassName ServiceManagerRegistry
 * @Description 服务注册到管理器中
 * @Author 潘语笛
 * @Date 23/12/2021 下午1:34
 * Version 1.0
 */
public class ServiceManagerRegistry implements Registry{
    ServiceManager serviceManager = ServiceManagerImpl.getSingleton();
    @Override
    public boolean check(Class<?> clazz) {
        return true;
    }

    @Override
    public void register(ServiceInfo serviceInfo) {
        serviceManager.addService(serviceInfo, new ServiceInvoker(serviceInfo.getBean()));
    }
}
