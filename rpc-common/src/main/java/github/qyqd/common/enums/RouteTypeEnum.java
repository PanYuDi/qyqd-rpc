package github.qyqd.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName RouteTypeEnum
 * @Description 路由类型
 * @Author 潘语笛
 * @Date 21/12/2021 下午2:21
 * Version 1.0
 */
@AllArgsConstructor
@Getter
public enum RouteTypeEnum {
    DIRECT(1, "直连模式", "direct"),
    NACOS_REGISTRY(2, "nacos注册中心模式", "nacos")
    ;
    Integer code;
    String msg;
    String typeName;
    public static RouteTypeEnum getTypeByName(String name) {
        for(RouteTypeEnum routeTypeEnum:RouteTypeEnum.values()) {
            if(routeTypeEnum.getTypeName().equals(name)) {
                return routeTypeEnum;
            }
        }
        return null;
    }
}
