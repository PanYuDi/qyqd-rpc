package github.qyqd.rpcexample.loadbalance.service;

import github.qyqd.remote.RpcServer;
import github.qyqd.remote.transport.netty.server.NettyServer;
import github.qyqd.spring.RpcBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午3:37
 * @Description: 服务端Rpc配置
 */
@Configuration
@ComponentScan("github.qyqd.rpcexample.loadbalance.service")
public class ServerRpcConfig {
    @Bean
    public RpcServer getServer() throws InterruptedException {
        RpcServer rpcServer = new NettyServer();
        return rpcServer;
    }
    @Bean
    public RpcBeanPostProcessor getRpcBeanPostProcessor() {
        return new RpcBeanPostProcessor();
    }
}
