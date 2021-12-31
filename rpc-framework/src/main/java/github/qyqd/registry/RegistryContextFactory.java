package github.qyqd.registry;

import github.qyqd.config.NacosConfig;
import github.qyqd.registry.nacos.NacosRegistry;

/**
 * @ClassName RegistryContextFactory
 * @Description 注册工厂类
 * @Author 潘语笛
 * @Date 23/12/2021 下午1:48
 * Version 1.0
 */
public class RegistryContextFactory implements RegistryFactory{
    @Override
    public Registry create() {
        RegistryContextImpl registryContext = new RegistryContextImpl();
        registryContext.addRegistry(new ServiceManagerRegistry());
        if(NacosConfig.enable) {
            registryContext.addRegistry(new NacosRegistry());
        }
        return registryContext;
    }
}
