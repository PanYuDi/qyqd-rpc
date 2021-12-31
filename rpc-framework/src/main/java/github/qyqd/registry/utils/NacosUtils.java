package github.qyqd.registry.utils;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import github.qyqd.common.exception.RpcException;
import github.qyqd.config.NacosConfig;
import github.qyqd.config.RpcConfig;
import github.qyqd.registry.Registry;
import github.qyqd.registry.RegistryMetadata;
import github.qyqd.rpc.invoker.Invocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: 潘语笛
 * @Date: 30/12/2021 下午8:27
 * @Description: nacos相关工具类
 */
public class NacosUtils {
    private static final String NACOS_SERVICE_NAME_PREFIX = "providers:";
    private static final String METHODS = "methods";
    private static final String INTERFACE_NAME = "interfaceName";
    private static final String CATEGORY = "category";
    private static final String BEAN_NAME = "beanName";
    private static final String TIMESTAMP = "timestamp";
    private static final String PROTOCOL = "qyqd";
    private static final String CLASS_NAME = "className";
    /**
     * 做一层缓存
     */
    private static ConcurrentMap<String, NamingService> namingServiceMap = new ConcurrentHashMap<>();
    NamingService namingService;
    public NacosUtils() throws NacosException {
        this(NacosConfig.serverAddr);
    }
    public NacosUtils(String serverAddr) throws NacosException {
        namingService = namingServiceMap.get(serverAddr);
        if(namingService == null) {
            namingService = NacosFactory.createNamingService(serverAddr);
            namingServiceMap.put(serverAddr, namingService);
        }
    }

    public void registerMetadata(RegistryMetadata metadata) throws NacosException {
        String serviceName = NACOS_SERVICE_NAME_PREFIX + metadata.getInterfaceName();
        Instance instance = new Instance();
        addRegistryMetadata(metadata, instance);
        namingService.registerInstance(serviceName, instance);
    }

    /**
     * 先只按照serviceName检索
     * @param invocation
     * @return
     */
    public RegistryMetadata lookupService(Invocation invocation) throws NacosException {
        String serviceName = NACOS_SERVICE_NAME_PREFIX + invocation.getInterfaceName();
        List<Instance> instances = namingService.selectInstances(serviceName, true);
        if(instances.isEmpty()) {
            throw new RpcException("cannot find nacos service " + serviceName);
        }
        // TODO 先选择第一个，以后加入负载均衡算法
        Instance instance = instances.get(0);
        RegistryMetadata metadata = getMetadata(instance);
        return metadata;
    }

    private void addRegistryMetadata(RegistryMetadata metadata, Instance instance) {
        String serviceName = getServiceName(metadata.getInterfaceName());
        instance.setIp(metadata.getServiceIp());
        instance.setPort(metadata.getServicePort());
        instance.setServiceName(serviceName);
        instance.setEnabled(true);
        instance.addMetadata(METHODS, metadata.getMethods().toString());
        instance.addMetadata(INTERFACE_NAME, metadata.getInterfaceName());
        instance.addMetadata(CATEGORY, metadata.getCategory());
        instance.addMetadata(BEAN_NAME, metadata.getBeanName());
        instance.addMetadata(TIMESTAMP, metadata.getBeanName());
        instance.addMetadata(PROTOCOL, metadata.getProtocol());
        instance.addMetadata(CLASS_NAME, metadata.getClazzName());
    }
    private RegistryMetadata getMetadata(Instance instance) {
        RegistryMetadata registryMetadata = new RegistryMetadata();
        Map<String, String> metadataMap = instance.getMetadata();
        registryMetadata.setBeanName(metadataMap.get(BEAN_NAME));
        registryMetadata.setCategory(metadataMap.get(CATEGORY));
        registryMetadata.setClazzName(metadataMap.get(CLASS_NAME));
        registryMetadata.setProtocol(metadataMap.get(PROTOCOL));
        registryMetadata.setTimestamp(Long.valueOf(metadataMap.get(TIMESTAMP)));
        String methodsS = metadataMap.get(METHODS);
        List<String> methods = Arrays.asList(methodsS.substring(1, methodsS.length() - 1).split(","));
        registryMetadata.setMethods(methods);
        registryMetadata.setServiceIp(instance.getIp());
        registryMetadata.setServicePort(instance.getPort());
        return registryMetadata;

    }
    public static String getServiceName(String interfaceName) {
        return NACOS_SERVICE_NAME_PREFIX + interfaceName;
    }
}
