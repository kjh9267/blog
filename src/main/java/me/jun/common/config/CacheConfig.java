package me.jun.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Collections.singletonList;

@Configuration
public class CacheConfig {

    @Bean
    CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        ConcurrentMapCache postsCache = new ConcurrentMapCache("postStore");
        List<ConcurrentMapCache> caches = singletonList(postsCache);
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }
}
