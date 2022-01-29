package github.qyqd.rpcexample.loadbalance.client;

import github.qyqd.annotations.RpcReference;
import org.springframework.stereotype.Component;

/**
 * @ClassName TestClientService
 * @Description TODO
 * @Author 潘语笛
 * @Date 29/12/2021 上午10:57
 * Version 1.0
 */
@Component
public class TestClientService {
    @RpcReference
    HelloService helloService;
    public String sayHello(String name) {
       return helloService.hello(name);
    }
}
