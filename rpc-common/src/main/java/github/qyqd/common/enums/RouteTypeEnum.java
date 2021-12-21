package github.qyqd.common.enums;

import lombok.AllArgsConstructor;

/**
 * @ClassName RouteTypeEnum
 * @Description 路由类型
 * @Author 潘语笛
 * @Date 21/12/2021 下午2:21
 * Version 1.0
 */
@AllArgsConstructor
public enum RouteTypeEnum {
    DIRECT(1, "直连模式"),
    NACOS_REGISTRY(2, "nacos注册中心模式")
    ;
    Integer code;
    String msg;
}
