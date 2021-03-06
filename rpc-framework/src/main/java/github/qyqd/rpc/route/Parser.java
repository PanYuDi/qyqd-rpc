package github.qyqd.rpc.route;

import github.qyqd.common.enums.RouteTypeEnum;

/**
 * @ClassName Parser
 * @Description 路由解析器
 * @Author 潘语笛
 * @Date 21/12/2021 下午1:52
 * Version 1.0
 * 路由设计：qyqd://type/route/interfaceName?parameters
 * type:路由类型， 目前计划实现直连 direct和 nacos
 * route：路由地址，可以是注册中心地址或者直接目标地址
 * interfaceName：接口名称
 * parameters：其他参数，按照以下方式填写： name1 = value1&name2 = value2
 *
 */
public interface Parser {

    /**
     * 解析路由地址
     * @param url
     * @return
     */
    BaseRoute parse(String url);

    /**
     * 解析出url对应的路由类型
     * @param url
     * @return
     */
    static RouteTypeEnum getRouteType(String url) {
        String typeName = url.substring(7).split("/")[0];
        return RouteTypeEnum.getTypeByName(typeName);
    }
}
