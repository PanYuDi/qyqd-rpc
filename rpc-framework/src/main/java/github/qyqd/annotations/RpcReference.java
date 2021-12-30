package github.qyqd.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @ClassName RpcReference
 * @Description 服务引用类
 * @Author 潘语笛
 * @Date 23/12/2021 下午5:20
 * Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RpcReference {
    String url() default "";
}
