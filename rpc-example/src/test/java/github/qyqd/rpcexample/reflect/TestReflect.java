package github.qyqd.rpcexample.reflect;

import github.qyqd.common.utils.ReflectUtils;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.rpc.invoker.RpcInvocation;
import github.qyqd.rpc.invoker.ServiceInvoker;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

/**
 * @ClassName TestReflect
 * @Description TODO
 * @Author 潘语笛
 * @Date 22/12/2021 上午11:22
 * Version 1.0
 */
public class TestReflect {
    @Test
    public void testSubclass() {
        System.out.println(ReflectUtils.isImplementation(RpcInvocation.class, Invocation.class));
    }
}
