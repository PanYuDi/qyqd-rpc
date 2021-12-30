package github.qyqd.registry;

/**
 * @ClassName RegistryContextFactory
 * @Description 注册工厂类
 * @Author 潘语笛
 * @Date 23/12/2021 下午1:48
 * Version 1.0
 */
public class RegistryContextFactory implements RegistryFactory{
    @Override
    public Registry create() {
        RegistryContextImpl registryContext = new RegistryContextImpl();
        registryContext.addRegistry(new ServiceManagerRegistry());
        return registryContext;
    }
}
