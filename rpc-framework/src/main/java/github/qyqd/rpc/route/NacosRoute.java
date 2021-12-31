package github.qyqd.rpc.route;

import github.qyqd.common.enums.RouteTypeEnum;
import lombok.Data;

/**
 * @ClassName NacosRoute
 * @Description Nacos路由信息
 * @Author 潘语笛
 * @Date 31/12/2021 上午9:59
 * Version 1.0
 */
@Data
public class NacosRoute extends BaseRoute{
    public NacosRoute() {
        this.routeType = RouteTypeEnum.NACOS_REGISTRY;
    }
    /**
     * nacos地址
     */
    String serverAddr;
    /**
     * 服务名称
     */
    String serviceName;

}
