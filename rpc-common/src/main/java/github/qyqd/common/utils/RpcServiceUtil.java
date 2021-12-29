package github.qyqd.common.utils;

import java.util.UUID;

/**
 * @ClassName RpcServiceUtil
 * @Description 服务相关工具类
 * @Author 潘语笛
 * @Date 23/12/2021 上午10:54
 * Version 1.0
 */
public class RpcServiceUtil {
    /**
     * 生成serviceName
     * @param beanName
     * @return
     */
    private final static String prefix = "qyqd:";
    public static String generateServiceName(Class<?> clazz) {
        return UUID.randomUUID().toString();
    }
}
