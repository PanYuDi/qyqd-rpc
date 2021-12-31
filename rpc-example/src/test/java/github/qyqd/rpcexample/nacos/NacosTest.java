package github.qyqd.rpcexample.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import github.qyqd.config.NacosConfig;
import github.qyqd.registry.utils.NacosUtils;
import org.junit.jupiter.api.Test;

/**
 * @ClassName NacosTest
 * @Description TODO
 * @Author 潘语笛
 * @Date 31/12/2021 下午4:55
 * Version 1.0
 */
public class NacosTest {
    @Test
    public void testNacos() throws NacosException {
        NamingService namingService = NacosFactory.createNamingService("1.15.113.171:8848");
        Instance instance = new Instance();
        instance.setIp("1.15.113.171");
        instance.setPort(8848);
        instance.setServiceName("abcd");
        instance.addMetadata("hello", "hi");
        namingService.registerInstance("abcd", instance);
    }
}
