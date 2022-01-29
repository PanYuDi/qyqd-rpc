package github.qyqd.common.extension;

import java.lang.annotation.*;

/**
 * @Author: 潘语笛
 * @Date: 25/1/2022 下午2:16
 * @Description: SPI注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {
    String value() default "";
}
