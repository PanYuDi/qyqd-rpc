package github.qyqd.rpcexample.proxy.service;

import github.qyqd.remote.RpcServer;
import github.qyqd.remote.entity.EndPoint;
import github.qyqd.remote.transport.netty.server.NettyServer;
import github.qyqd.spring.ClientBeanPostProcessor;
import github.qyqd.spring.RpcBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午3:37
 * @Description: TODO
 */
@Configuration
@ComponentScan("github.qyqd.rpcexample")
public class RpcConfig {
    @Bean
    public RpcServer getServer() throws InterruptedException {
        RpcServer rpcServer = new NettyServer();
        return rpcServer;
    }
    @Bean
    public RpcBeanPostProcessor getRpcBeanPostProcessor() {
        return new RpcBeanPostProcessor();
    }
    @Bean
    public ClientBeanPostProcessor getClientBeanPostProcessor() {
        return new ClientBeanPostProcessor();
    }
}
