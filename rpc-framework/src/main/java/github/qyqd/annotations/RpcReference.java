package github.qyqd.annotations;

/**
 * @ClassName RpcReference
 * @Description 服务引用类
 * @Author 潘语笛
 * @Date 23/12/2021 下午5:20
 * Version 1.0
 */
public @interface RpcReference {
    String url() default "";

    String serviceName() default "";
}
