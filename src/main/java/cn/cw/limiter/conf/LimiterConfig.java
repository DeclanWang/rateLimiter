package cn.cw.limiter.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.Serializable;

/**
 * 限流属性配置类
 *
 * @author WangCong
 * @since 2018-12-17
 */
@Configuration
public class LimiterConfig {

    /**
     * 读取限流脚本
     */
    @Bean
    public DefaultRedisScript<Number> redisLuaScript() {
        DefaultRedisScript<Number> redisLuaScript = new DefaultRedisScript<>();
        redisLuaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimit.lua")));
        redisLuaScript.setResultType(Number.class);
        return redisLuaScript;
    }

    /**
     * Redis模板
     */
    @Bean(name = "limitRedisTemplate")
    public RedisTemplate<String, Serializable> limitRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
