package github.qyqd.registry.nacos;

import github.qyqd.registry.Registry;
import github.qyqd.scanner.ServiceInfo;

/**
 * @ClassName NacosRegistry
 * @Description Nacos注册
 * @Author 潘语笛
 * @Date 30/12/2021 下午4:20
 * Version 1.0
 */
public class NacosRegistry implements Registry {
    @Override
    public boolean check(Class<?> clazz) {
        return true;
    }

    @Override
    public void register(ServiceInfo serviceInfo) {

    }
}
