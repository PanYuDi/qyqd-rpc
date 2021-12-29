//package github.qyqd.spring;
//
//import github.qyqd.annotations.RpcReference;
//import github.qyqd.common.exception.RpcException;
//import github.qyqd.config.RpcConfig;
//import github.qyqd.factory.RpcBeanFactory;
//import github.qyqd.factory.RpcBeanFactoryImpl;
//import io.protostuff.Rpc;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanNameGenerator;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ResourceLoaderAware;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.core.type.AnnotationMetadata;
//import org.springframework.core.type.classreading.MetadataReader;
//import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
//import org.springframework.util.ClassUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @Author: 潘语笛
// * @Date: 27/12/2021 下午8:31
// * @Description: 搜索包，并把代理类注册到容器中,这里只生成一个假的对象
// */
//public class RpcProxyRegistry implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, ApplicationContextAware {
//    ResourcePatternResolver resourcePatternResolver;
//    String SEARCH_SUFFIX = "**/*.class";
//    ApplicationContext context;
//    private static final String BEAN_NAME_PREFIX = "qyqd-client:";
//    @Override
//    public void setResourceLoader(ResourceLoader resourceLoader) {
//        this.resourcePatternResolver = (ResourcePatternResolver) resourceLoader;
//    }
//
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
//        RpcBeanFactory rpcBeanFactory = new RpcBeanFactoryImpl();
//        Set<Class<?>> classes = searchResources();
//        for(Class<?> clazz:classes) {
//            RpcReference annotation = (RpcReference) getClientAnnotation(clazz);
//            Object bean = rpcBeanFactory.createBean(annotation.serviceName(), annotation.url(), clazz);
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
//            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//            definition.getConstructorArgumentValues().addGenericArgumentValue(annotation);
//            definition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
//            definition.setBeanClass(RpcClientFactoryBean.class);
//            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME);
//            registry.registerBeanDefinition(BEAN_NAME_PREFIX + clazz.getSimpleName(), definition);
//        }
//    }
//
//    private Set<Class<?>> searchResources() {
//        Set<Class<?>> clazzSet = new HashSet<>();
//        String[] basePackages = RpcConfig.BASE_PACKAGES;
//        for(String basePackage:basePackages) {
//            String searchPattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(basePackage) + File.separator + SEARCH_SUFFIX;
//            try {
//                Resource[] resources = resourcePatternResolver.getResources(searchPattern);
//                for(Resource resource:resources) {
//                    MetadataReader metadataReader = new SimpleMetadataReaderFactory().getMetadataReader(resource);
//                    String className = metadataReader.getClassMetadata().getClassName();
//                    Class clazz = Class.forName(className);
//                    if(getClientAnnotation(clazz) != null) {
//                        clazzSet.add(clazz);
//                    }
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//                throw new RpcException("client read resources failed");
//            }
//        }
//        return clazzSet;
//    }
//    private Annotation getClientAnnotation(Class<?> clazz) {
//        return clazz.getDeclaredAnnotation(RpcReference.class);
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.context = applicationContext;
//    }
//}
