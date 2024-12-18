package me.jun.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Configuration
public class CacheConfig {

    @Bean
    CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        ConcurrentMapCache postStore = new ConcurrentMapCache("postStore");
        ConcurrentMapCache memberStore = new ConcurrentMapCache("memberStore");
        List<ConcurrentMapCache> caches = unmodifiableList(
                Arrays.asList(
                        postStore,
                        memberStore
        ));
        simpleCacheManager.setCaches(caches);
        return simpleCacheManager;
    }
}
