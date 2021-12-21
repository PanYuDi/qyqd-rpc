package github.qyqd.rpc.route;

import github.qyqd.common.enums.RouteTypeEnum;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName AbstractRoute
 * @Description 基础路由
 * @Author 潘语笛
 * @Date 21/12/2021 下午1:56
 * Version 1.0
 */
@Data
public class BaseRoute {
    /**
     * 原始路由地址
     */
    String url;
    /**
     * 接口名称
     */
    String interfaceName;
    /**
     * 路由类型
     */
    RouteTypeEnum routeType;
    /**
     * 路由
     */
    String route;
    /**
     * 其他参数
     */
    Map<String, Object> parameters = new HashMap<>();
}
