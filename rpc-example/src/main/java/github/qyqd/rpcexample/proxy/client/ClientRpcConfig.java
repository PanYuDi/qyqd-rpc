package github.qyqd.rpcexample.proxy.client;

import github.qyqd.spring.ClientBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ClientRpcConfi
 * @Description TODO
 * @Author 潘语笛
 * @Date 31/12/2021 下午5:17
 * Version 1.0
 */
@Configuration
@ComponentScan("github.qyqd.rpcexample")
public class ClientRpcConfig {
    @Bean
    public ClientBeanPostProcessor getClientBeanPostProcessor() {
        return new ClientBeanPostProcessor();
    }
}
