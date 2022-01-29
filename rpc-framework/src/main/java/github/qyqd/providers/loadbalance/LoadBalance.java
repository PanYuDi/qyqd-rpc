package github.qyqd.providers.loadbalance;

import github.qyqd.common.extension.SPI;
import github.qyqd.rpc.invoker.Invocation;

import java.util.List;

/**
 * @ClassName LoadBalance
 * @Description TODO
 * @Author 潘语笛
 * @Date 12/1/2022 下午2:14
 * Version 1.0
 */
@SPI
public interface LoadBalance {
    public Invocation choose(List<Invocation> invocationList);

    /**
     * 实现容错机制
     * @param invocationList
     * @return
     */
    public Invocation chooseNext(List<Invocation> invocationList);
}
