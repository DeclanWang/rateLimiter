package cn.cw.limiter.conf;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

/**
 * Fastjson配置类
 *
 * @author WangCong
 * @since 2018-12-17
 */
@Configuration
@ConditionalOnClass(JSONObject.class)
public class FastjsonConfig {

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(supportedMediaTypes);

        return converter;
    }
}