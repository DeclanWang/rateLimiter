package cn.cw.limiter.controller;

import cn.cw.limiter.aop.annotation.RateLimit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 默认控制器
 *
 * @author WangCong
 * @since 2018-12-17
 */
@RestController
public class IndexController {

    /**
     * 测试
     *
     * @return success字符串
     */
    @RateLimit(key = "test")
    @GetMapping("/limiter/test")
    public Mono<ResponseEntity<String>> test() {
        return Mono.justOrEmpty(new ResponseEntity<>("success", HttpStatus.OK));
    }
}
