package github.qyqd.rpcexample.spi;

import github.qyqd.common.extension.ExtensionLoader;

/**
 * @Author: 潘语笛
 * @Date: 27/1/2022 下午8:33
 * @Description: TODO
 */
public class TestSPI {
    public static void main(String[] args) {
        TestHello testHello = ExtensionLoader.getExtensionLoader(TestHello.class).getExtension();
        System.out.println(testHello.sayHello());
    }
}
