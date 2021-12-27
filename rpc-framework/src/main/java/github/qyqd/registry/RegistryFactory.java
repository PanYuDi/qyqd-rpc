package github.qyqd.registry;

/**
 * @ClassName RegistryFactory
 * @Description 注册创建工厂
 * @Author 潘语笛
 * @Date 23/12/2021 下午1:47
 * Version 1.0
 */
public interface RegistryFactory {
    /**
     * 创建registry
     * @return
     */
    public Registry create();
}
