package github.qyqd.rpcexample.spi;

import github.qyqd.common.extension.SPI;

/**
 * @Author: 潘语笛
 * @Date: 27/1/2022 下午6:23
 * @Description: 测试SPI
 */
@SPI(value = "my")
public interface TestHello {
    public String sayHello();
}
