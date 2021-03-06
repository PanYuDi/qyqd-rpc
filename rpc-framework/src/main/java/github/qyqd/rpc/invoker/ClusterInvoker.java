package github.qyqd.rpc.invoker;

import github.qyqd.common.extension.ExtensionLoader;
import github.qyqd.config.ExtensionConfig;
import github.qyqd.providers.Provider;
import github.qyqd.providers.RouteProvider;
import github.qyqd.providers.loadbalance.LoadBalance;
import github.qyqd.providers.loadbalance.RandomLoadBalance;
import github.qyqd.rpc.result.Result;

import java.util.List;

/**
 * @ClassName ClusterInvoker
 * @Description 负载均衡执行器，目标是实现调用负载均衡算法和高可用
 * @Author 潘语笛
 * @Date 13/1/2022 下午4:07
 * Version 1.0
 */
public class ClusterInvoker extends AbstractInvoker {
    List<Invocation> invocations;
    LoadBalance loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(ExtensionConfig.loadBalance);
    Invoker invoker = new ClientServiceInvoker();
    public ClusterInvoker(List<Invocation> invocations) {
        this.invocations = invocations;
    }

    @Override
    public Result doInvoke(Invocation invocation) throws Throwable {
        Invocation choose = loadBalance.choose(invocations);
        Result result = null;
        while(choose != null) {
            result = invoker.invoke(choose);
            if(result.getException() != null) {
                choose = loadBalance.chooseNext(invocations);
            } else {
                break;
            }
        }
        return result;
    }
}
