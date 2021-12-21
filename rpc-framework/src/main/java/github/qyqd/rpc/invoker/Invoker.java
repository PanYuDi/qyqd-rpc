package github.qyqd.rpc.invoker;

import github.qyqd.rpc.result.Result;

/**
 * @ClassName Invoker
 * @Description 实际的方法执行者
 * @Author 潘语笛
 * @Date 21/12/2021 上午11:17
 * Version 1.0
 */
public interface Invoker {

    /**
     * 实际执行服务
     * @param invocation 服务调用参数
     * @return
     */
    Result invoke(Invocation invocation);

}
