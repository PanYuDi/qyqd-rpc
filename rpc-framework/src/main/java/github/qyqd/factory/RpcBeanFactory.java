package github.qyqd.factory;

/**
 * @Author: 潘语笛
 * @Date: 26/12/2021 下午2:49
 * @Description:  对外接口，创建rpc代理类
 */
public interface RpcBeanFactory {
    /**
     * 创建一个rpc代理bean
     * @param serviceName 服务名称，默认为接口名称
     * @param url 服务路由，现在必填，以后通过配置的方式默认选择响应的注册中心
     * @param clazz 接口类型
     * @param <T> 泛型
     * @return
     */
    public <T> T createBean(String serviceName, String url, Class<T> clazz);

    public <T> T createBean(String url, Class<T> clazz);

}
