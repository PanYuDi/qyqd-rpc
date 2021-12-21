package github.qyqd.annotations;

/**
 * @Author: PanYuDi
 * @Date: 21/12/2021 下午8:11
 * @Description: 用于标明服务实现类
 */
public @interface ServiceProvider {
    /**
     * 指定的服务名称，服务注册时应提供默认值
     * @return
     */
    String serviceName();
}
