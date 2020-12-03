package cn.niudehua.springbootdemo.config;

import cn.niudehua.springbootdemo.interceptor.RateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 类名称：WebConfiguration
 * ***********************
 * <p>
 * 类描述：Web配置
 *
 * @author deng on 2020/11/29下午11:17
 */
@EnableWebMvc
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebConfiguration implements WebMvcConfigurer {
    private final RateLimitInterceptor rateLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/Users/deng/IdeaProjects/springbootdemo/uploads/");
    }
}