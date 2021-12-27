package github.qyqd.rpc.proxy;

import github.qyqd.common.exception.RpcException;
import github.qyqd.rpc.invoker.Invocation;

import java.lang.reflect.Proxy;

/**
 * @Author: 潘语笛
 * @Date: 25/12/2021 下午8:23
 * @Description: 使用JDK动态代理生成代理类
 */
public class JdkProxyFactory implements ProxyFactory {

    @Override
    public Object createProxy(Invocation invocation) {
        Class<?> interfaceType = null;
        try {
            interfaceType = Class.forName(invocation.getInterfaceName());
        } catch (ClassNotFoundException e) {
            throw new RpcException("should never happened:interface class not found");
        }
        return Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, new ClientInvocationHandler(invocation));
    }
}
