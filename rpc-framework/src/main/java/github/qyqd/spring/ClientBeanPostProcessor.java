package github.qyqd.spring;

import github.qyqd.annotations.RpcReference;
import github.qyqd.common.exception.RpcException;
import github.qyqd.factory.RpcBeanFactory;
import github.qyqd.factory.RpcBeanFactoryImpl;
import github.qyqd.rpc.invoker.Invocation;
import github.qyqd.rpc.invoker.RpcInvocation;
import github.qyqd.rpc.proxy.JdkProxyFactory;
import github.qyqd.rpc.proxy.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @ClassName ClientBeanPostProcessor
 * @Description TODO
 * @Author 潘语笛
 * @Date 29/12/2021 上午10:28
 * Version 1.0
 */
public class ClientBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcBeanFactory rpcBeanFactory = new RpcBeanFactoryImpl();
        //获取该bean的所有字段
        Field[] fields = bean.getClass().getDeclaredFields();
        for(Field field:fields) {
            RpcReference annotation = field.getDeclaredAnnotation(RpcReference.class);
            if(annotation != null) {
                field.setAccessible(true);
                try {
                    field.set(bean, rpcBeanFactory.createBean(annotation.url(), field.getType()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RpcException("should not happened: field set failed!");
                }
            }
        }
        return bean;
    }
}
