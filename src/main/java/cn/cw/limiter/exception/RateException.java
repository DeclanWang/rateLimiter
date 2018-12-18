package cn.cw.limiter.exception;

/**
 * 速率异常
 *
 * @author WangCong
 * @since 2018-12-18
 */
public class RateException extends RuntimeException {


    public RateException() {
        super();
    }


    public RateException(String message) {
        super(message);
    }


    public RateException(String message, Throwable cause) {
        super(message, cause);
    }


    public RateException(Throwable cause) {
        super(cause);
    }
}
