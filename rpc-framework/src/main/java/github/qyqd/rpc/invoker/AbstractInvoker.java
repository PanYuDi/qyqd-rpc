package github.qyqd.rpc.invoker;

import github.qyqd.common.exception.BizException;
import github.qyqd.common.exception.RpcException;
import github.qyqd.rpc.result.Result;
import github.qyqd.rpc.result.RpcResult;

/**
 * @Author: PanYuDi
 * @Date: 21/12/2021 下午8:48
 * @Description: 抽象执行器，主要是对异常进行处理
 */
public abstract class AbstractInvoker implements Invoker {
    @Override
    public Result invoke(Invocation invocation) {
        try {
            return doInvoke(invocation);
        } catch (RpcException e) {
            // 是RPC框架抛出来的异常
            return new RpcResult(e);
        } catch (Throwable e) {
            // 业务异常
            return new RpcResult(new BizException(e, "encounter exception while invoke rpc service " + invocation.getInterfaceName()));
        }
    }

    /**
     * 执行服务
     * @param invocation
     * @return
     * @throws Throwable
     */
    public abstract Result doInvoke(Invocation invocation) throws Throwable;
}
