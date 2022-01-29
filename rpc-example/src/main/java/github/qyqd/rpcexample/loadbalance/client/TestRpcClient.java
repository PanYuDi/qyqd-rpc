package github.qyqd.rpcexample.loadbalance.client;

import github.qyqd.config.ExtensionConfig;
import github.qyqd.config.NacosConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Random;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午3:36
 * @Description: TODO
 */
@Slf4j
public class TestRpcClient {
    public static void main(String[] args) throws InterruptedException {
        NacosConfig.serverAddr = "1.15.113.171:8848";
        ExtensionConfig.loadBalance = "consistent";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientRpcConfig.class);
        TestClientService testClientService = context.getBean(TestClientService.class);

        for (int i = 0; i < 20; i++) {
            Thread.sleep(1000);
            int r = new Random(System.currentTimeMillis()).nextInt(3);
            String hello = testClientService.sayHello("pyd" + Integer.valueOf(r).toString());
            log.info(hello);
        }
    }
}
