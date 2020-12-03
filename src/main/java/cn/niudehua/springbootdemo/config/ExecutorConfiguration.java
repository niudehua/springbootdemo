package cn.niudehua.springbootdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 类名称：ExecutorConfiguration
 * ***********************
 * <p>
 * 类描述：线程池配置
 *
 * @author deng on 2020/11/30下午8:40
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfiguration {
    @Bean(name = "exportExcelServiceExecutor")
    public Executor exportExcelServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数量：当前机器的核心数
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        // 队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 线程池中的线程名前缀
        executor.setThreadNamePrefix("exportExcel-");
        // 拒绝策略(中止策略)
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 执行初始化
        executor.initialize();
        return executor;
    }

}