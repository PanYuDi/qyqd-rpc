package github.qyqd.rpc.route;

import github.qyqd.common.enums.RouteTypeEnum;

/**
 * @Author: 潘语笛
 * @Date: 25/12/2021 上午10:49
 * @Description: 直连型路由解析器
 * qyqd://direct/127.0.0.1:8001/interfaceName
 */
public class DirectRouteParser implements Parser {

    @Override
    public BaseRoute parse(String url) {
        String[] sep = url.substring(7).split("/");
        BaseRoute route = new BaseRoute();
        route.setRouteType(RouteTypeEnum.DIRECT);
        route.setRoute(url);
        route.setHost(sep[1].split(":")[0]);
        route.setPort(Integer.valueOf(sep[1].split(":")[1]));
        route.setInterfaceName(sep[2]);
        return route;
    }
}
