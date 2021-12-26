package github.qyqd.rpc.invoker;

import github.qyqd.rpc.proxy.JdkProxyFactory;
import github.qyqd.rpc.proxy.ProxyFactory;
import github.qyqd.rpc.result.Result;
import github.qyqd.rpc.result.RpcResult;

/**
 * @ClassName ProxyInvoker
 * @Description 实现动态代理执行器
 * @Author 潘语笛
 * @Date 23/12/2021 下午5:31
 * Version 1.0
 */
// TODO
public class ProxyInvoker extends AbstractInvoker{
    ProxyFactory proxyFactory = new JdkProxyFactory();
    @Override
    public Result doInvoke(Invocation invocation) {
        Object proxy = proxyFactory.createProxy(invocation);
        RpcResult rpcResult = new RpcResult();
        rpcResult.setValue(proxy);
        return rpcResult;
    }
}
