package github.qyqd.providers.loadbalance;

import github.qyqd.common.exception.RpcException;
import github.qyqd.rpc.invoker.Invocation;

import java.util.List;

/**
 * @ClassName AbstractLoadBalance
 * @Description 实现容错机制的存储
 * @Author 潘语笛
 * @Date 13/1/2022 下午4:12
 * Version 1.0
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    ThreadLocal<Integer> currentChoose = new ThreadLocal<>();
    ThreadLocal<Integer> firstChoose = new ThreadLocal<>();
    @Override
    public Invocation choose(List<Invocation> invocationList) {
        int i = doChoose(invocationList);
        currentChoose.set(i);
        firstChoose.set(i);
        return invocationList.get(i);
    }
    public abstract int doChoose(List<Invocation> invocationList);

    @Override
    public Invocation chooseNext(List<Invocation> invocationList) {
        if(currentChoose.get() == null) {
            throw new RpcException("should never happened");
        }
        int i = doChooseNext(invocationList, currentChoose.get());
        currentChoose.set(i);
        if(i == firstChoose.get()) {
            //穷举完毕，没有可用的实例
            return null;
        } else {
            return invocationList.get(i);
        }
    }
    public abstract int doChooseNext(List<Invocation> invocationList, int previousChoose);
}
