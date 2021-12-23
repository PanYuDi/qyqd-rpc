package github.qyqd.providers;

import github.qyqd.common.exception.RpcException;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.scanner.ServiceInfo;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName SpringContextServiceManager
 * @Description 通过spring容器管理服务
 * @Author 潘语笛
 * @Date 22/12/2021 下午2:15
 * Version 1.0
 */
public class ServiceManagerImpl implements ServiceManager{
    Map<String, Invoker> serviceMap = new ConcurrentHashMap<>();
    private static ServiceManagerImpl singleton = new ServiceManagerImpl();
    // 单例
    private ServiceManagerImpl() {
    }

    public static ServiceManagerImpl getSingleton() {
        return singleton;
    }
    @Override
    public Invoker getInvoker(Invocation invocation) {
        return serviceMap.get(invocation.getServiceName());
    }

    @Override
    public void addService(ServiceInfo serviceInfo, Invoker invoker) {
        Invoker invoker1 = serviceMap.putIfAbsent(serviceInfo.getServiceName(), invoker);
        if(invoker1 != null) {
            throw new RpcException("service name duplicated");
        }
    }

    @Override
    public Invoker removeService(String serviceName) {
        return serviceMap.remove(serviceName);
    }

    @Override
    public Invoker getInvokerByName(String serviceName) {
        return serviceMap.get(serviceName);
    }
}
