package github.qyqd.rpcexample.proxy.client;

import github.qyqd.factory.RpcBeanFactoryImpl;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午3:36
 * @Description: TODO
 */
public class TestRpcClient {
    public static void main(String[] args) {
        HelloService helloService = new RpcBeanFactoryImpl().createBean("qyqd://direct/127.0.0.1:8088/", HelloService.class);
        System.out.println(helloService.hello());
    }
}
