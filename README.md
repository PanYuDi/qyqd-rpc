# qyqd-rpc
## 前言
写这个RPC框架自然不是为了给别人在生产环境用的，作为一个工程经验还不那么丰富的在校小白，我个人再怎么写也是不可能比dubbo写的好的。主要还是供自己学习，在做中学学中做，了解rpc实现过程中的各项原理和设计模式。当然还有比较功利的想法，明年秋招写到简历里作为项目经验争取助我捞几个offer
以后还计划在博客写下自己实现过程中遇到的技术重点,敬请期待
https://www.cnblogs.com/PanYuDi/

目前实现了以下功能

1. 直连模式调用rpc服务
2. 通过工厂创建代理对象
3. 通过注解注入代理对象，spring管理


1.1计划实现的功能：


1. nacos注册中心连接模式

# 使用方法

1. 引入framework包

```xml
<dependency>
    <groupId>rpc</groupId>
    <artifactId>rpc-framework</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

2. 创建一个接口

```java
public interface HelloService {
    public String hello(String name);
}
```

3. 在服务端编写接口的实现并标记注解

```java
@RpcProvider(interfaceType = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hi " + name;
    }
}

```

4. 编写服务启动类，这里通过spring管理,做了两件事，创建服务启动类和注册前置处理器

```java
@Configuration
@ComponentScan("github.qyqd.rpcexample")
public class RpcConfig {
    @Bean
    public RpcServer getServer() throws InterruptedException {
        RpcServer rpcServer = new NettyServer();
        return rpcServer;
    }
    @Bean
    public RpcBeanPostProcessor getRpcBeanPostProcessor() {
        return new RpcBeanPostProcessor();
    }
    @Bean
    public ClientBeanPostProcessor getClientBeanPostProcessor() {
        return new ClientBeanPostProcessor();
    }
}

```

5. server端启动服务

```java
public class ServerMain {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new AnnotationConfigApplicationContext(RpcConfig.class);
        RpcServer server = context.getBean(RpcServer.class);
        server.start();
    }
}

```
6. 编写业务类,放入容器
```java
@Component
public class TestClientService {
    @RpcReference(url = "qyqd://direct/127.0.0.1:8000/")
    HelloService helloService;
    public String sayHello() {
       return helloService.hello("pyd");
    }
}

```
7. client端调用服务

```java
public class TestRpcClient {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RpcConfig.class);
        TestClientService testClientService = context.getBean(TestClientService.class);
        String hello = testClientService.sayHello();
        log.info(hello);
    }
}
```

