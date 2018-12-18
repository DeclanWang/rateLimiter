package cn.cw.limiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 限流应用
 *
 * @author WangCong
 * @since 2018-12-17
 */
@SpringBootApplication
public class LimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LimiterApplication.class, args);
	}

}

