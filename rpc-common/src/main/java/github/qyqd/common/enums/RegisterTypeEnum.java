package github.qyqd.common.enums;

import lombok.AllArgsConstructor;

/**
 * @Author: 潘语笛
 * @Date: 30/12/2021 下午9:46
 * @Description: 注册中心注册时的种类
 */
@AllArgsConstructor
public enum RegisterTypeEnum {
    PROVIDER("provider", "服务提供者");
    String name;
    String msg;
}
