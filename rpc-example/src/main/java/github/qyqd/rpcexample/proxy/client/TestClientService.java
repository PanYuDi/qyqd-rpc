package github.qyqd.rpcexample.proxy.client;

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
    @RpcReference(url = "qyqd://direct/127.0.0.1:8000/")
    HelloService helloService;
    public String sayHello() {
       return helloService.hello("pyd");
    }
}
