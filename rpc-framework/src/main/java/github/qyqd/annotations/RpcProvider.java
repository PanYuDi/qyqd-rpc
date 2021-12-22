package github.qyqd.annotations;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: PanYuDi
 * @Date: 21/12/2021 下午8:11
 * @Description: 用于标明服务实现类
 */
@Component
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Primary
@Inherited
public @interface RpcProvider {
    /**
     * 指定的服务名称，服务注册时应提供默认值
     * @return
     */
    String serviceName();
}
