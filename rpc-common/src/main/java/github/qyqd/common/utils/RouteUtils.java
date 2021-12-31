package github.qyqd.common.utils;

import github.qyqd.common.enums.RouteTypeEnum;

/**
 * @ClassName RouteUtils
 * @Description 生成路由地址
 * @Author 潘语笛
 * @Date 31/12/2021 上午9:35
 * Version 1.0
 */
public class RouteUtils {
    private static final String ROUTE_PREFIX = "qyqd://";
    private static final String COLON = ":";
    private static final String BACK_LASH = "/";
    /**
     * 生成直连url，示例：
     * qyqd://direct/127.0.0.1:8000/
     * @param ip
     * @param port
     * @return
     */
    public static String generateDirectUrl(String ip, Integer port) {
        return ROUTE_PREFIX + RouteTypeEnum.DIRECT.getTypeName() + BACK_LASH + ip + COLON + port.toString() + BACK_LASH;
    }

    /**
     * 生成Nacos路由地址
     * 示例：qyqd://nacos/127.0.0.1:8848/service.HelloService/
     * @param serverAddr
     * @param serviceName
     * @return
     */
    public static String generateNacosUrl(String serverAddr, String serviceName) {
        return ROUTE_PREFIX + RouteTypeEnum.NACOS_REGISTRY.getTypeName() + BACK_LASH + serverAddr + BACK_LASH + serviceName + BACK_LASH;
    }

}
