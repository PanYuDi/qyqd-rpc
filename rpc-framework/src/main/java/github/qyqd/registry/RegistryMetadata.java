package github.qyqd.registry;

import lombok.Data;

import java.util.List;

/**
 * @ClassName RegistryMetadata
 * @Description 注册中心上最终注册信息
 * @Author 潘语笛
 * @Date 30/12/2021 下午5:00
 * Version 1.0
 */
@Data
public class RegistryMetadata {
    List<String> methods;

    String interfaceName;

    String category;

    String beanName;

    Long timestamp;

    String protocol = "qyqd";

    String clazzName;

}
