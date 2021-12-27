package github.qyqd.rpcexample.spring.service;

import github.qyqd.providers.Provider;
import github.qyqd.providers.ServiceManager;
import github.qyqd.providers.ServiceManagerImpl;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.rpc.invoker.RpcInvocation;
import github.qyqd.rpc.result.Result;
import github.qyqd.rpcexample.spring.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName SpringExample
 * @Description TODO
 * @Author 潘语笛
 * @Date 23/12/2021 下午4:53
 * Version 1.0
 */
public class SpringExample {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Provider provider = ServiceManagerImpl.getSingleton();
        RpcInvocation build = RpcInvocation.builder().serviceName("test").interfaceName(TestService.class.getName()).methodName("test").build();
        Invoker test = provider.getInvoker(build);
        Result invoke = test.invoke(build);
        System.out.println(invoke.getValue());
    }
}
