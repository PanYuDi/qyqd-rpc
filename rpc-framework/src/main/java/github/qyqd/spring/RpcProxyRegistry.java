package github.qyqd.spring;

import github.qyqd.annotations.RpcReference;
import github.qyqd.common.exception.RpcException;
import github.qyqd.config.RpcConfig;
import github.qyqd.factory.RpcBeanFactory;
import github.qyqd.factory.RpcBeanFactoryImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: 潘语笛
 * @Date: 27/12/2021 下午8:31
 * @Description: 搜索包，并把代理类注册到容器中
 */
public class RpcProxyRegistry implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, ApplicationContextAware {
    ResourcePatternResolver resourcePatternResolver;
    String SEARCH_SUFFIX = "**/*.class";
    ApplicationContext context;
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = (ResourcePatternResolver) resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RpcBeanFactory rpcBeanFactory = new RpcBeanFactoryImpl();
        Set<Class<?>> classes = searchResources();
        for(Class<?> clazz:classes) {
            Annotation annotation = getClientAnnotation(clazz.getAnnotations());
            Object bean = rpcBeanFactory.createBean(((RpcReference)getClientAnnotation(clazz.getAnnotations())).serviceName(), ((RpcReference)getClientAnnotation(clazz.getAnnotations())).url(), clazz);
            registry.
        }
    }

    private Set<Class<?>> searchResources() {
        Set<Class<?>> clazzSet = new HashSet<>();
        String[] basePackages = RpcConfig.BASE_PACKAGES;
        for(String basePackage:basePackages) {
            String searchPattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(basePackage) + File.separator + SEARCH_SUFFIX;
            try {
                Resource[] resources = resourcePatternResolver.getResources(searchPattern);
                for(Resource resource:resources) {
                    MetadataReader metadataReader = new SimpleMetadataReaderFactory().getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class clazz = Class.forName(className);
                    if(getClientAnnotation(clazz.getAnnotations()) != null) {
                        clazzSet.add(clazz);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                throw new RpcException("client read resources failed");
            }
        }
        return clazzSet;
    }
    private Annotation getClientAnnotation(Annotation[] annotations) {
        for(Annotation annotation:annotations) {
            if(annotation instanceof RpcReference) {
                return annotation;
            }
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
