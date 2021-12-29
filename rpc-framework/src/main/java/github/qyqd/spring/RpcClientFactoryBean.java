//package github.qyqd.spring;
//
//import github.qyqd.annotations.RpcReference;
//import github.qyqd.factory.RpcBeanFactory;
//import github.qyqd.factory.RpcBeanFactoryImpl;
//import org.springframework.beans.factory.FactoryBean;
//
///**
// * @ClassName RpcClientFactoryBean
// * @Description 注册bean
// * @Author 潘语笛
// * @Date 28/12/2021 上午9:30
// * Version 1.0
// */
//public class RpcClientFactoryBean implements FactoryBean {
//    Class<?> clazz;
//    RpcReference rpcReference;
//    RpcBeanFactory rpcBeanFactory = new RpcBeanFactoryImpl();
//    public RpcClientFactoryBean(RpcReference rpcReference, Class<?> clazz) {
//        this.clazz = clazz;
//        this.rpcReference = rpcReference;
//    }
//    @Override
//    public Object getObject() throws Exception {
////        if(!rpcReference.serviceName().isEmpty()) {
////            return rpcBeanFactory.createBean(rpcReference.serviceName(), rpcReference.url(), clazz);
////        } else {
////            return rpcBeanFactory.createBean(rpcReference.url(), clazz);
////        }
//        return null;
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return clazz;
//    }
//}
