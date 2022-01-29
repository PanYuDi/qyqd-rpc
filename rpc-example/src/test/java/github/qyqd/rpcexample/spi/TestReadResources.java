package github.qyqd.rpcexample.spi;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @Author: 潘语笛
 * @Date: 27/1/2022 下午6:31
 * @Description: TODO
 */
public class TestReadResources {
    @Test
    public void test() throws IOException {
        ClassLoader classLoader = TestReadResources.class.getClassLoader();
        String fileName = "META-INF/extensions/github.qyqd.rpcexample.spi.TestHello";
        Enumeration<URL> resources = classLoader.getResources(fileName);
        URL url = resources.nextElement();
        System.out.println(url.openStream().read());
    }
}
