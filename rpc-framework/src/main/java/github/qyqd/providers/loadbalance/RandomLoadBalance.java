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
public class RandomLoadBalance implements LoadBalance{
    @Override
    public Invocation choose(List<Invocation> invocationList) {
        Random random = new Random();
        int i = random.nextInt(invocationList.size());
        return invocationList.get(i);
    }
}
