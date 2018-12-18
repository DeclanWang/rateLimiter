package cn.cw.limiter.aop.annotation;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author WangCong
 * @since 2018-12-17
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 令牌唯一标识
     */
    String key() default "";

    /**
     * 请求令牌的数量，默认置为一
     */
    int permits() default 1;
}
