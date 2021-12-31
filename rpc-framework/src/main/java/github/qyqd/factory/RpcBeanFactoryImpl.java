package github.qyqd.factory;

import github.qyqd.common.exception.RpcException;
import github.qyqd.providers.DefaultProvider;
import github.qyqd.providers.Provider;
import github.qyqd.providers.RouteProvider;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.rpc.invoker.RpcInvocation;
import io.protostuff.Rpc;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午3:04
 * @Description: bean工厂实现类
 */
public class RpcBeanFactoryImpl implements RpcBeanFactory {
    Provider provider = new DefaultProvider();
    @Override
    public <T> T createBean(String url, Class<T> clazz) {
        if(!clazz.isInterface()) {
            throw new RpcException("only interface can be passed");
        }
        Invocation invocation = new RpcInvocation();
        invocation.setUrl(url);
        invocation.setInterfaceName(clazz.getName());
        Invoker invoker = provider.getInvoker(invocation);
        return (T)invoker.invoke(invocation).getValue();
    }

}
