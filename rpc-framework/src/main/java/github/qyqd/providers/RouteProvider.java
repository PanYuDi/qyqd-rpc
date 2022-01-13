package github.qyqd.providers;

import github.qyqd.common.enums.RouteTypeEnum;
import github.qyqd.providers.nacos.NacosProvider;
import github.qyqd.rpc.invoker.ClientServiceInvoker;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.rpc.invoker.ProxyInvoker;
import github.qyqd.rpc.route.Parser;

/**
 * @Author: 潘语笛
 * @Date: 25/12/2021 下午9:34
 * @Description: 通过route来动态获取Invoker
 */
public class RouteProvider implements Provider{
    @Override
    public Invoker getInvoker(Invocation invocation) {
        String url = invocation.getUrl();
        RouteTypeEnum routeTypeEnum = Parser.getRouteType(url);
        switch (routeTypeEnum) {
            case DIRECT:
                return new ClientServiceInvoker();
            case NACOS_REGISTRY:
                return new NacosProvider().getInvoker(invocation);
            default:
                return null;
        }
    }
}
