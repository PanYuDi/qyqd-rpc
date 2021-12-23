package github.qyqd.registry;

import github.qyqd.scanner.ServiceInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName RegistryContext
 * @Description
 * @Author 潘语笛
 * @Date 23/12/2021 上午10:27
 * Version 1.0
 */
@Slf4j
public class RegistryContext implements Registry {
    List<Registry> registryList = new CopyOnWriteArrayList<>();
    public void addRegistry(Registry registry) {
        registryList.add(registry);
    }
    @Override
    public boolean check(Class<?> clazz) {
        return true;
    }

    @Override
    public void register(ServiceInfo serviceInfo) {
        for(Registry registry:registryList) {
            if(registry.check(serviceInfo.getType())) {
                try {
                    registry.register(serviceInfo);
                } catch (Exception e) {
                    log.error("register service failed");
                    e.printStackTrace();
                }
            }
        }
    }
}
