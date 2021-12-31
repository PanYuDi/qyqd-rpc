package github.qyqd.providers;

import github.qyqd.common.utils.RouteUtils;
import github.qyqd.config.NacosConfig;
import github.qyqd.registry.utils.NacosUtils;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;

/**
 * @ClassName DefaultProvider
 * @Description 默认提供者,适用于不提供url的情况
 * @Author 潘语笛
 * @Date 31/12/2021 下午3:41
 * Version 1.0
 */
public class DefaultProvider implements Provider {
    Provider nextProvider = new RouteProvider();
    @Override
    public Invoker getInvoker(Invocation invocation) {
        // TODO 目前写死成nacos
        if(invocation.getUrl().isEmpty() || invocation.getUrl() == null) {
            invocation.setUrl(RouteUtils.generateNacosUrl(NacosConfig.serverAddr, NacosUtils.getServiceName(invocation.getInterfaceName())));
        }
        return nextProvider.getInvoker(invocation);
    }
}
