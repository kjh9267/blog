package me.jun.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
public class LockConfig {

    @Bean
    @Qualifier("createArticleLock")
    public Lock createArticleLock() {
        return new ReentrantLock();
    }

    @Bean
    @Qualifier("createTagToArticleLock")
    public Lock createTagToAritcleLock() {
        return new ReentrantLock();
    }
}
