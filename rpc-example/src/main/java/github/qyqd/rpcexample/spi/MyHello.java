package github.qyqd.rpcexample.spi;

/**
 * @Author: 潘语笛
 * @Date: 27/1/2022 下午6:24
 * @Description: TODO
 */
public class MyHello implements TestHello{
    @Override
    public String sayHello() {
        return "hello";
    }
}
