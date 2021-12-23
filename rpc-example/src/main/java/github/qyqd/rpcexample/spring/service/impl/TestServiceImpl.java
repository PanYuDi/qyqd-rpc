package github.qyqd.rpcexample.spring.service.impl;

import github.qyqd.annotations.RpcProvider;
import github.qyqd.rpcexample.spring.service.TestService;

/**
 * @ClassName TestServiceImpl
 * @Description TODO
 * @Author 潘语笛
 * @Date 23/12/2021 下午4:43
 * Version 1.0
 */
@RpcProvider(serviceName = "test", interfaceType = TestService.class)
public class TestServiceImpl implements TestService {
    @Override
    public String test() {
        return "hello world";
    }
}
