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
    NamingService namingService = null;
    public NacosUtils() throws NacosException {
        namingService = NacosFactory.createNamingService(NacosConfig.serverAddr);
    }

    public void registerMetadata(String key, RegistryMetadata metadata) {
        Instance instance = new Instance();
        instance.setIp(RpcConfig.HOST);
        instance.setPort(RpcConfig.PORT);
    }
}
