package github.qyqd.providers.nacos;

import com.alibaba.nacos.api.config.convert.NacosConfigConverter;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import github.qyqd.common.exception.RpcException;
import github.qyqd.common.utils.RouteUtils;
import github.qyqd.config.NacosConfig;
import github.qyqd.providers.AbstractCachedProvider;
import github.qyqd.providers.Provider;
import github.qyqd.providers.RouteProvider;
import github.qyqd.registry.RegistryMetadata;
import github.qyqd.registry.utils.NacosUtils;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.Invoker;
import github.qyqd.rpc.invoker.RpcInvocation;
import github.qyqd.rpc.route.NacosRoute;
import github.qyqd.rpc.route.NacosRouteParser;
import github.qyqd.rpc.route.Parser;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName NacosProvider
 * @Description Nacos执行器,主要任务是将nacos路由转换为direct路由
 * @Author 潘语笛
 * @Date 31/12/2021 上午9:29
 * Version 1.0
 */
public class NacosProvider extends AbstractCachedProvider {
    private NacosRouteParser parser = new NacosRouteParser();
    NacosUtils nacosUtils = new NacosUtils(NacosConfig.serverAddr);
    @Override
    public List<Invocation> getInvocation(Invocation invocation) {
        NacosRoute route = parser.parse(invocation.getUrl());
        try {
            RegistryMetadata metadata = nacosUtils.lookupService(invocation);
            String directUrl = RouteUtils.generateDirectUrl(metadata.getServiceIp(), metadata.getServicePort());
            invocation.setUrl(directUrl);
            return Arrays.asList(invocation);
        } catch (NacosException e) {
            e.printStackTrace();
            throw new RpcException("client connect nacos failed, serverAddr = " + route.getServerAddr());
        }
    }

    /**
     * 监听nacos-server，更新
     * @param invocation
     */
    @Override
    public void subscribe(Invocation invocation) {
        nacosUtils.subscribe(invocation.getServiceName(), (event -> {
            NamingEvent namingEvent = ((NamingEvent) event);
            List<Invocation> invocationList = namingEvent.getInstances().stream().map(instance->metadataToInvocation(nacosUtils.getMetadata(instance))).collect(Collectors.toList());
            updateInvocation(invocation.getServiceName(), invocationList, UpdateStatusEnum.UPDATE);
        }));
    }
    private Invocation metadataToInvocation(RegistryMetadata metadata) {
        Invocation invocation = new RpcInvocation();
        invocation.setInterfaceName(metadata.getInterfaceName());
        invocation.setUrl(RouteUtils.generateDirectUrl(metadata.getServiceIp(), metadata.getServicePort()));
        return invocation;
    }
}
