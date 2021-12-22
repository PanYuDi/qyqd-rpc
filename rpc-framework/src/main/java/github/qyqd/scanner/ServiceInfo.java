package github.qyqd.scanner;

import lombok.Data;

/**
 * @ClassName ServiceInfo
 * @Description 服务信息
 * @Author 潘语笛
 * @Date 22/12/2021 下午3:24
 * Version 1.0
 */
@Data
public class ServiceInfo {
    Object bean;
    String interfaceName;
    String serviceName;
}
