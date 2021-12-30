package github.qyqd.registry;

import github.qyqd.scanner.ServiceInfo;

/**
 * @ClassName Registry
 * @Description 服务注册中心
 * @Author 潘语笛
 * @Date 22/12/2021 下午3:04
 * Version 1.0
 */
public interface Registry {
    /**
     * 判断该类是否能被注册
     * @param clazz
     * @return
     */
    boolean check(Class<?> clazz);

    /**
     * 注册服务
     * @param serviceInfo
     */
    void register(ServiceInfo serviceInfo);

    /**
     * 删除一个服务
     */
    default void unregister(ServiceInfo serviceInfo) {

    }

}
