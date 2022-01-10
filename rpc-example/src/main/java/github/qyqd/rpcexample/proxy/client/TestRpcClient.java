package github.qyqd.rpcexample.proxy.client;

import github.qyqd.config.NacosConfig;
import github.qyqd.factory.RpcBeanFactoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午3:36
 * @Description: TODO
 */
@Slf4j
public class TestRpcClient {
    public static void main(String[] args) throws InterruptedException {
        NacosConfig.serverAddr = "1.15.113.171:8848";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientRpcConfig.class);
        TestClientService testClientService = context.getBean(TestClientService.class);
        String hello = testClientService.sayHello();
        log.info(hello);
    }
}
