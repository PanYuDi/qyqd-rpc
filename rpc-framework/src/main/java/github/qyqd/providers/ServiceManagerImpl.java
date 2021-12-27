package github.qyqd.providers;

import github.qyqd.common.exception.RpcException;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.scanner.ServiceInfo;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName SpringContextServiceManager
 * @Description 通过spring容器管理服务
 * @Author 潘语笛
 * @Date 22/12/2021 下午2:15
 * Version 1.0
 */
public class ServiceManagerImpl implements ServiceManager{
    Map<String, Invoker> serviceNameMap = new ConcurrentHashMap<>();
    Map<String, List<Invoker>> interfaceNameMap = new ConcurrentHashMap<>();
    private static ServiceManagerImpl singleton = new ServiceManagerImpl();
    // 单例
    private ServiceManagerImpl() {
    }

    public static ServiceManagerImpl getSingleton() {
        return singleton;
    }
    @Override
    public Invoker getInvoker(Invocation invocation) {
        // 先通过serviceName找服务
        Invoker invoker = serviceNameMap.get(invocation.getServiceName());
        // 然后通过接口名
        if(invoker == null) {
            List<Invoker> invokerList = interfaceNameMap.get(invocation.getInterfaceName());
            if(invokerList != null && invokerList.size() > 0) {
                invoker = invokerList.get(0);
            }
        }
        if(invoker == null) {
            throw new RpcException("can not find service " + invocation);
        }
        return invoker;
    }

    @Override
    public void addService(ServiceInfo serviceInfo, Invoker invoker) {
        Invoker invoker1 = serviceNameMap.putIfAbsent(serviceInfo.getServiceName(), invoker);
        if(invoker1 != null) {
            throw new RpcException("service name duplicated");
        }
        interfaceNameMap.putIfAbsent(serviceInfo.getInterfaceName(), new CopyOnWriteArrayList<>());
        interfaceNameMap.get(serviceInfo.getInterfaceName()).add(invoker);
    }

}
