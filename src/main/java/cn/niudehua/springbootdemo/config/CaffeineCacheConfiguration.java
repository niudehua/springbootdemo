package cn.niudehua.springbootdemo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 类名称：CaffeineCacheConfiguration
 * ***********************
 * <p>
 * 类描述：缓存管理器
 *
 * @author deng on 2020/11/29下午9:21
 */
@Configuration
@EnableCaching
@Slf4j
public class CaffeineCacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        // 缓存集合
        List<CaffeineCache> caffeineCacheList = new ArrayList<>();
        caffeineCacheList.add(new CaffeineCache("customer_cache", Caffeine.newBuilder()
                // 指定最大缓存量
                .maximumSize(1000)
                // 过期时间
                .expireAfterWrite(120, TimeUnit.SECONDS)
                .build()));
        cacheManager.setCaches(caffeineCacheList);
        return cacheManager;
    }
}