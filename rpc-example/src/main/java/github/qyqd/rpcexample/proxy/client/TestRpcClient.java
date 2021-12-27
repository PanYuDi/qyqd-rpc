package github.qyqd.rpcexample.proxy.client;

import github.qyqd.factory.RpcBeanFactoryImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午3:36
 * @Description: TODO
 */
@Slf4j
public class TestRpcClient {
    public static void main(String[] args) {
        HelloService helloService = new RpcBeanFactoryImpl().createBean("qyqd://direct/127.0.0.1:8000/", HelloService.class);
        String hello = helloService.hello("pyd");
        log.info(hello);
    }
}
