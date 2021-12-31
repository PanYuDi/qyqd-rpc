package github.qyqd.providers;

import com.alibaba.nacos.api.exception.NacosException;
import github.qyqd.common.exception.RpcException;
import github.qyqd.common.utils.RouteUtils;
import github.qyqd.registry.RegistryMetadata;
import github.qyqd.registry.utils.NacosUtils;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.rpc.route.NacosRoute;
import github.qyqd.rpc.route.NacosRouteParser;
import github.qyqd.rpc.route.Parser;

/**
 * @ClassName NacosProvider
 * @Description Nacos执行器,主要任务是将nacos路由转换为direct路由
 * @Author 潘语笛
 * @Date 31/12/2021 上午9:29
 * Version 1.0
 */
public class NacosProvider implements Provider{
    private Provider nextProvider = new RouteProvider();
    private NacosRouteParser parser = new NacosRouteParser();
    @Override
    public Invoker getInvoker(Invocation invocation) {
        NacosRoute route = parser.parse(invocation.getUrl());
        try {
            NacosUtils nacosUtils = new NacosUtils(route.getServerAddr());
            RegistryMetadata metadata = nacosUtils.lookupService(invocation);
            String directUrl = RouteUtils.generateDirectUrl(metadata.getServiceIp(), metadata.getServicePort());
            invocation.setUrl(directUrl);
            return nextProvider.getInvoker(invocation);
        } catch (NacosException e) {
            e.printStackTrace();
            throw new RpcException("client connect nacos failed, serverAddr = " + route.getServerAddr());
        }
    }
}
