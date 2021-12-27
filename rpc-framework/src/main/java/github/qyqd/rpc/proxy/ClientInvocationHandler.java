package github.qyqd.rpc.proxy;

import github.qyqd.remote.message.rpc.RpcResponse;
import github.qyqd.rpc.invoker.ClientServiceInvoker;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.rpc.result.Result;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: 潘语笛
 * @Date: 25/12/2021 下午8:10
 * @Description: jdk代理的handler类
 */
public class ClientInvocationHandler implements InvocationHandler {
    private static Invoker invoker = new ClientServiceInvoker();
    Invocation invocation;
    public ClientInvocationHandler(Invocation invocation) {
        this.invocation = invocation;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        invocation.setMethodName(method.getName());
        invocation.setParameters(args);
        Class<?>[] types = null;
        if (args != null) {
            types = new Class[args.length];
            for(int i = 0; i < args.length; i++) {
                types[i] = args[i].getClass();
            }
        }
        invocation.setParameterTypes(types);
        Result result = invoker.invoke(invocation);
        // 处理异常
        if(result.getException() != null) {
            throw result.getException();
        }
        return ((RpcResponse)result.getValue()).getResult();
    }
}
