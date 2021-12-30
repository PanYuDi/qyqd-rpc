package github.qyqd.registry.utils;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import github.qyqd.config.NacosConfig;
import github.qyqd.config.RpcConfig;
import github.qyqd.registry.Registry;
import github.qyqd.registry.RegistryMetadata;

/**
 * @Author: 潘语笛
 * @Date: 30/12/2021 下午8:27
 * @Description: nacos相关工具类
 */
public class NacosUtils {
    private final String NACOS_SERVICE_NAME_PREFIX = "providers:";
    private final String METHODS = "methods";
    private final String INTERFACE_NAME = "interfaceName";
    private final String CATEGORY = "category";
    private final String BEAN_NAME = "beanName";
    private final String TIMESTAMP = "timestamp";
    private final String PROTOCOL = "quqd";
    private final String CLASS_NAME = "className";
    NamingService namingService = null;
    public NacosUtils() throws NacosException {
        namingService = NacosFactory.createNamingService(NacosConfig.serverAddr);
    }

    public void registerMetadata(RegistryMetadata metadata) throws NacosException {
        String serviceName = NACOS_SERVICE_NAME_PREFIX + metadata.getInterfaceName();
        Instance instance = new Instance();
        instance.setIp(RpcConfig.HOST);
        instance.setPort(RpcConfig.PORT);
        instance.setServiceName(serviceName);
        instance.setEnabled(true);
        instance.addMetadata(METHODS, metadata.getMethods().toString());
        instance.addMetadata(INTERFACE_NAME, metadata.getInterfaceName());
        instance.addMetadata(CATEGORY, metadata.getCategory());
        instance.addMetadata(BEAN_NAME, metadata.getBeanName());
        instance.addMetadata(TIMESTAMP, metadata.getBeanName());
        instance.addMetadata(PROTOCOL, metadata.getProtocol());
        instance.addMetadata(CLASS_NAME, metadata.getClazzName());
        namingService.registerInstance(serviceName, instance);
    }
}
