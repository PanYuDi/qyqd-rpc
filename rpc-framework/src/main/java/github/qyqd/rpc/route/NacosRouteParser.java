package github.qyqd.rpc.route;

/**
 * @ClassName NacosRouteParser
 * @Description 解析nacos路由
 * @Author 潘语笛
 * 示例：qyqd://nacos/127.0.0.1:8848/service.HelloService
 * @Date 31/12/2021 上午9:59
 * Version 1.0
 */
public class NacosRouteParser implements Parser {
    @Override
    public NacosRoute parse(String url) {
        String[] sep = url.substring(7).split("/");
        NacosRoute nacosRoute = new NacosRoute();
        nacosRoute.setServerAddr(sep[1]);
        nacosRoute.setServiceName(sep[2]);
        return nacosRoute;
    }
}
