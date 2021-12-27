package github.qyqd.rpcexample.proxy.service;

import github.qyqd.annotations.RpcProvider;
import github.qyqd.rpcexample.proxy.client.HelloService;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午3:35
 * @Description: TODO
 */
@RpcProvider(interfaceType = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hi " + name;
    }
}
