package github.qyqd.rpcexample.proxy.service;

import github.qyqd.config.NacosConfig;
import github.qyqd.remote.RpcServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName ServerMain
 * @Description TODO
 * @Author 潘语笛
 * @Date 27/12/2021 上午9:37
 * Version 1.0
 */
public class ServerMain {
    public static void main(String[] args) throws InterruptedException {
        // 配置注册中心
        NacosConfig.serverAddr = "1.15.113.171:8848";
        ApplicationContext context = new AnnotationConfigApplicationContext(ServerRpcConfig.class);
        RpcServer server = context.getBean(RpcServer.class);
        server.start();
    }
}
