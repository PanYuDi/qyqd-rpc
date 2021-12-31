package github.qyqd.annotations;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: PanYuDi
 * @Date: 21/12/2021 下午8:11
 * @Description: 用于标明服务实现类,用这个注解标注的类会直接进spring
 */
@Component
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Primary
@Inherited
public @interface RpcProvider {

    /**
     * 实现方法的接口
     * @return
     */
    Class<?> interfaceType();
}
