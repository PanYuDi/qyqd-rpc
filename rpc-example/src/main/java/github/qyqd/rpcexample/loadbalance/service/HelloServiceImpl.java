package github.qyqd.rpcexample.loadbalance.service;

import github.qyqd.annotations.RpcProvider;
import github.qyqd.config.RpcConfig;
import github.qyqd.rpcexample.loadbalance.client.HelloService;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午3:35
 * @Description: TODO
 */
@RpcProvider(interfaceType = HelloService.class)
public class HelloServiceImpl implements HelloService {
    Integer port = RpcConfig.PORT;
    @Override
    public String hello(String name) {
        return "hi " + name + " and my port is " + port;
    }
}
