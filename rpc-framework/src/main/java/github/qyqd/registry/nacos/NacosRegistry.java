package github.qyqd.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import github.qyqd.common.enums.RegisterTypeEnum;
import github.qyqd.common.exception.RpcException;
import github.qyqd.registry.AsyncRegistry;
import github.qyqd.registry.Registry;
import github.qyqd.registry.RegistryMetadata;
import github.qyqd.registry.utils.NacosUtils;
import github.qyqd.scanner.ServiceInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName NacosRegistry
 * @Description Nacos注册
 * @Author 潘语笛
 * @Date 30/12/2021 下午4:20
 * Version 1.0
 */
public class NacosRegistry extends AsyncRegistry {
    NacosUtils nacosUtils;

    {
        try {
            nacosUtils = new NacosUtils();
        } catch (NacosException e) {
            e.printStackTrace();
            throw new RpcException("Nacos connect failed");
        }
    }

    @Override
    public void doRegister(ServiceInfo serviceInfo) throws NacosException {
        RegistryMetadata registryMetadata = new RegistryMetadata();
        List<String> methods = new ArrayList<>();
        for(Method method:serviceInfo.getInterfaceType().getMethods()) {
            methods.add(method.getName());
        }
        registryMetadata.setMethods(methods);
        registryMetadata.setBeanName(serviceInfo.getBeanName());
        registryMetadata.setCategory(RegisterTypeEnum.PROVIDER.name());
        registryMetadata.setTimestamp(System.currentTimeMillis());
        registryMetadata.setClazzName(serviceInfo.getType().getName());
        registryMetadata.setInterfaceName(serviceInfo.getInterfaceName());
        nacosUtils.registerMetadata(registryMetadata);
    }

    @Override
    public boolean check(Class<?> clazz) {
        return true;
    }
}
