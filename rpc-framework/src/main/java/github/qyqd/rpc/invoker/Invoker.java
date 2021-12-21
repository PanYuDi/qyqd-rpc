package github.qyqd.rpc.invoker;

import github.qyqd.rpc.Result;

/**
 * @ClassName Invoker
 * @Description 实际的方法执行者
 * @Author 潘语笛
 * @Date 21/12/2021 上午11:17
 * Version 1.0
 */
public interface Invoker {
    /**
     * 执行方法调用
     * @param parameters
     * @param parameterTypes
     * @return
     */
    Result invoke(Object[] parameters, Class<?> parameterTypes);

}
