package github.qyqd.spring;

import github.qyqd.annotations.RpcProvider;
import github.qyqd.common.exception.RpcException;
import github.qyqd.common.utils.ReflectUtils;
import github.qyqd.common.utils.RpcServiceUtil;
import github.qyqd.registry.Registry;
import github.qyqd.registry.RegistryContextFactory;
import github.qyqd.scanner.ServiceInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author: 潘语笛
 * @Date: 22/12/2021 下午9:52
 * @Description: rpc bean前置处理器，处理被annotation标注过的对象
 */
@Component
public class RpcBeanPostProcessor implements BeanPostProcessor {
    // TODO 创建这个类
    Registry registry = new RegistryContextFactory().create();
    // 处理rpc服务提供者的注册
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcProvider annotation = beanClass.getAnnotation(RpcProvider.class);
        if(annotation != null) {
            // 检查信息是否填写正确
            if(!ReflectUtils.isImplementation(bean.getClass(), annotation.interfaceType())) {
                throw new RpcException(String.format("RpcProvider %s is not the implementation of the declared service %s", beanName, annotation.interfaceType().getName()));
            }
            String serviceName = null;
            // 如果服务名为空则自动为其命名
            if(annotation.serviceName().isEmpty()) {
                serviceName = RpcServiceUtil.generateServiceName(beanClass);
            } else {
                serviceName = annotation.serviceName();
            }
            // 构建信息
            ServiceInfo serviceInfo = ServiceInfo.builder().bean(bean).serviceName(serviceName).type(bean.getClass()).interfaceType(annotation.interfaceType()).interfaceName(annotation.interfaceType().getName()).build();
            // 注册服务
            if(registry.check(beanClass)) {
                registry.register(serviceInfo);
            }
        }
        return bean;
    }
}
