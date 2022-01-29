package github.qyqd.providers.loadbalance;

import github.qyqd.rpc.invoker.Invocation;

import java.util.List;
import java.util.Random;

/**
 * @ClassName RandomLoadBalance
 * @Description 随机选择一个
 * @Author 潘语笛
 * @Date 12/1/2022 下午2:26
 * Version 1.0
 */
public class RandomLoadBalance extends AbstractLoadBalance{
    @Override
    public int doChoose(List<Invocation> invocationList) {
        Random random = new Random();
        int i = random.nextInt(invocationList.size());
        return i;
    }

    @Override
    public int doChooseNext(List<Invocation> invocationList, int previousChoose) {
        return (previousChoose + 1) % invocationList.size();
    }
}
