package cn.cw.limiter.aop;

import cn.cw.limiter.aop.annotation.RateLimit;
import cn.cw.limiter.exception.RateException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 限流切面处理
 *
 * @author WangCong
 * @since 2018-12-17
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class RateLimitAspect {

    private final DefaultRedisScript<Number> redisLuaScript;

    private final RedisTemplate<String, Serializable> limitRedisTemplate;

    private final HttpServletRequest request;

    @Autowired
    public RateLimitAspect(DefaultRedisScript<Number> redisLuaScript,
                           RedisTemplate<String, Serializable> limitRedisTemplate,
                           HttpServletRequest request) {
        this.redisLuaScript = redisLuaScript;
        this.limitRedisTemplate = limitRedisTemplate;
        this.request = request;
    }


    /**
     * 切入点
     */
    @Pointcut("@annotation(cn.cw.limiter.aop.annotation.RateLimit)")
    public void limit() {
    }


    @Around("limit()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Class<?> clazz = pjp.getTarget().getClass();
        String methodName = pjp.getSignature().getName();
        Method method = clazz.getMethod(methodName);
        RateLimit rateLimit = method.getDeclaredAnnotation(RateLimit.class);

        if (rateLimit != null) {
            String ip = request.getRemoteAddr();

            List<String> keys = Collections.singletonList(ip + "-" + rateLimit.key());

            Number result = limitRedisTemplate.execute(redisLuaScript, keys, rateLimit.permits(), System.currentTimeMillis());

            if (result.intValue() != 1) {
                log.info("too many request");
                throw new RateException("服务器处理不过来啦！");
            } else {
                pjp.proceed();
            }
        }
        return pjp.proceed();
    }
}
