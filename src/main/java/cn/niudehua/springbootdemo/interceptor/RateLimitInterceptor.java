package cn.niudehua.springbootdemo.interceptor;

import cn.niudehua.springbootdemo.enums.ErrorCodeEnum;
import cn.niudehua.springbootdemo.exception.BizException;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类名称：RateLimitInterceptor
 * ***********************
 * <p>
 * 类描述：全局限流拦截器
 *
 * @author deng on 2020/11/29下午11:12
 */
@Slf4j
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(10);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!RATE_LIMITER.tryAcquire()) {
            log.error("系统已被限流");
            throw new BizException(ErrorCodeEnum.RATE_LIMITER_ERROR);
        }
        log.info("系统未被限流，正常执行");
        return true;
    }

}