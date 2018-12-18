package cn.cw.limiter.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理类
 *
 * @author WangCong
 * @since 2018-12-17
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = RateException.class)
    public JSONObject defaultProcess(RateException exception, HttpServletResponse httpServletResponse) {
        int status = HttpStatus.TOO_MANY_REQUESTS.value();

        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.TOO_MANY_REQUESTS.value());
        response.put("msg", exception.getMessage());

        httpServletResponse.setStatus(status);
        return response;
    }

}
