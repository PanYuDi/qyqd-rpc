package github.qyqd.providers;

import github.qyqd.common.exception.RpcException;
import github.qyqd.providers.loadbalance.LoadBalance;
import github.qyqd.providers.loadbalance.RandomLoadBalance;
import github.qyqd.rpc.invoker.ClusterInvoker;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import org.springframework.beans.BeanUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName AbstactCacheableProvider
 * @Description 带缓存的Invoker提供者
 * @Author 潘语笛
 * @Date 12/1/2022 上午11:24
 * Version 1.0
 */
public abstract class AbstractCachedProvider implements Provider, Observer {
    protected Map<String, CopyOnWriteArrayList<Invocation>> invocationDirectory = new ConcurrentHashMap<>();

    @Override
    public void updateInvocation(String serviceName, List<Invocation> invocation, UpdateStatusEnum status) {
        switch (status) {
            case UPDATE:
                if(invocationDirectory.containsKey(serviceName)) {
                    invocationDirectory.compute(serviceName, (k,v) -> new CopyOnWriteArrayList<>(invocation));
                }
                break;
            default:
        }
    }

    @Override
    public Invoker getInvoker(Invocation invocation) {
        List<Invocation> invocations;
        if(invocationDirectory.containsKey(invocation.getInterfaceName())) {
            invocations = invocationDirectory.get(invocation.getInterfaceName());
        } else {
            invocationDirectory.putIfAbsent(invocation.getInterfaceName(), new CopyOnWriteArrayList<>());
            invocations = getInvocation(invocation);
            if(invocations == null) {
                throw new RpcException("cannot find rpc service " + invocation.getInterfaceName());
            }
            invocationDirectory.compute(invocation.getInterfaceName(), (k,v)->{
                v.addAll(invocations);
                return v;
            });
            // 监听这个服务
            subscribe(invocation);
        }
        invocations.forEach(invocation1 -> {
            invocation1.setMethodName(invocation.getMethodName());
            invocation1.setInterfaceName(invocation.getInterfaceName());
            invocation1.setParameters(invocation.getParameters());
            invocation1.setParameterTypes(invocation.getParameterTypes());
        });
        Invoker invoker = new ClusterInvoker(invocations);
        return invoker;

    }

    /**
     * 获取下一次调用的invocation
     * @param invocation
     * @return
     */
    public abstract List<Invocation> getInvocation(Invocation invocation);
}
