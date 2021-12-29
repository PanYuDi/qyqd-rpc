# qyqd-rpc

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

