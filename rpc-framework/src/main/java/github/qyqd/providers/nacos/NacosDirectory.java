package github.qyqd.providers.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import github.qyqd.common.enums.RouteTypeEnum;
import github.qyqd.common.exception.RpcException;
import github.qyqd.common.utils.RouteUtils;
import github.qyqd.config.NacosConfig;
import github.qyqd.providers.Directory;
import github.qyqd.registry.RegistryMetadata;
import github.qyqd.registry.utils.NacosUtils;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.RpcInvocation;
import github.qyqd.rpc.route.NacosRoute;
import github.qyqd.rpc.route.NacosRouteParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 潘语笛
 * @Date: 28/1/2022 上午9:44
 * @Description: 在Nacos上搜索服务
 */
@Slf4j
public class NacosDirectory implements Directory {
    private static final String ROUTE_PREFIX = "qyqd://";
    private static final String COLON = ":";
    private static final String BACK_LASH = "/";
    NacosRouteParser nacosRouteParser = new NacosRouteParser();
    NacosUtils nacosUtils = new NacosUtils(NacosConfig.serverAddr);
    @Override
    public List<Invocation> searchInvocation(Invocation invocation) {
        NacosRoute route = nacosRouteParser.parse(invocation.getUrl());
        try {
            List<RegistryMetadata> metadata = nacosUtils.lookupService(invocation);
            List<Invocation> invocations = metadata.stream().map(m->{
                Invocation newInvocation = new RpcInvocation();
                BeanUtils.copyProperties(invocation, newInvocation);
                newInvocation.setUrl(RouteUtils.generateDirectUrl(m.getServiceIp(), m.getServicePort()));
                return newInvocation;
            }).collect(Collectors.toList());
            return invocations;
        } catch (NacosException e) {
            e.printStackTrace();
            throw new RpcException("client connect nacos failed, serverAddr = " + route.getServerAddr());
        }
    }

    @Override
    public void subscribe(Invocation invocation, DirectoryListener directoryListener) {
        nacosUtils.subscribe(invocation.getServiceName(), (event -> {
            NamingEvent namingEvent = ((NamingEvent) event);
            List<Invocation> invocationList = namingEvent.getInstances().stream().map(instance->metadataToInvocation(nacosUtils.getMetadata(instance))).collect(Collectors.toList());
            log.debug("receive nacos event:{}", invocationList);
            directoryListener.onEvent(invocationList);
        }));
    }

    @Override
    public String generateUrl(Invocation invocation) {
        return ROUTE_PREFIX + RouteTypeEnum.NACOS_REGISTRY.getTypeName() + BACK_LASH + NacosConfig.serverAddr + BACK_LASH + invocation.getInterfaceName() + BACK_LASH;
    }

    private Invocation metadataToInvocation(RegistryMetadata metadata) {
        Invocation invocation = new RpcInvocation();
        invocation.setInterfaceName(metadata.getInterfaceName());
        invocation.setUrl(RouteUtils.generateDirectUrl(metadata.getServiceIp(), metadata.getServicePort()));
        return invocation;
    }
}
