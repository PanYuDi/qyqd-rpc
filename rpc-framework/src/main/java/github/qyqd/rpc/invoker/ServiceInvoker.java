package github.qyqd.rpc.invoker;

import github.qyqd.common.exception.BizException;
import github.qyqd.common.exception.RpcException;
import github.qyqd.common.utils.ReflectUtils;
import github.qyqd.rpc.result.Result;
import github.qyqd.rpc.result.RpcResult;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @ClassName ServiceInvoker
 * @Description 服务端通过具体对象执行方法
 * @Author 潘语笛
 * @Date 22/12/2021 上午10:56
 * Version 1.0
 */
@Slf4j
public class ServiceInvoker extends AbstractInvoker {
    Object targetService;
    public ServiceInvoker(Object targetService) {
        this.targetService = targetService;
    }
    @Override
    public Result doInvoke(Invocation invocation) throws Throwable {
        log.debug("begin invoke target instance, parameters is {}", invocation);
        log.debug("target service type is {}", targetService.getClass().getName());
        try {
            Method method = targetService.getClass().getMethod(invocation.getMethodName());
            Object result = method.invoke(targetService, invocation.getParameters());
            return new RpcResult(result);
        } catch (NoSuchMethodException e) {
            throw new RpcException("method not exist");
        } catch (IllegalAccessException e) {
            throw new RpcException("illegal method access");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private void checkParameters(Invocation invocation) {
        try {
            // 获取指定类型
            Class<?> requireType = Class.forName(invocation.getInterfaceName());
            Class<?> targetType = targetService.getClass();
            // 不是接口
            if(!requireType.isInterface()) {
                throw new RpcException("rpc can only handle interface");
            }
            // 目标是否是接口的实现类
            if(!ReflectUtils.isImplementation(targetType, requireType)) {
                throw new RpcException("target mismatch the interface");
            }
        } catch (ClassNotFoundException e) {
            throw new RpcException("target service not found!");
        }
    }
}
