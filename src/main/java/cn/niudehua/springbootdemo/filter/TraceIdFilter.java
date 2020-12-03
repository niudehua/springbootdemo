package cn.niudehua.springbootdemo.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * 类名称：TraceIdFilter
 * ***********************
 * <p>
 * 类描述：TraceId过滤器
 *
 * @author deng on 2020/11/29下午11:56
 */
@WebFilter("/*")
@Order(1)
public class TraceIdFilter implements Filter {
    private final static String TRACE_ID = "traceId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String traceId = servletRequest.getParameter(TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = TRACE_ID + UUID.randomUUID();
        }
        MDC.put(TRACE_ID, traceId);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}