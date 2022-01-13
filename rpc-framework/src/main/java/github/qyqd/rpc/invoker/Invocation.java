package github.qyqd.rpc.invoker;

/**
 * @Author: PanYuDi
 * @Date: 21/12/2021 下午8:24
 * @Description:
 */
public interface Invocation {
    /**
     * 接口全类名
     * @return
     */
    String getInterfaceName();

    /**
     * 获取方法名
     * @return
     */
    String getMethodName();

    /**
     * 获取参数类型
     * @return
     */
    Class<?>[] getParameterTypes();

    /**
     * 获取参数
     * @return
     */
    Object[] getParameters();

    /**
     * 获取url
     * @return
     */
    String getUrl();

    /**
     * 获取该服务的key
     * @return
     */
    String getServiceName();
    void setMethodName(String methodName);

    void setParameterTypes(Class<?>[] parameterTypes);

    void setParameters(Object[] parameters);

    void setInterfaceName(String interfaceName);

    void setUrl(String url);

}
