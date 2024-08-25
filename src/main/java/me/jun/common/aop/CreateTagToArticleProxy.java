package me.jun.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

@Aspect
@Component
@Order(1)
public class CreateTagToArticleProxy {

    @Autowired
    @Qualifier("createTagToArticleLock")
    private Lock createTagToArticleLock;

    @Around("execution(* me.jun.blog.application.TaggedArticleService.createTagToArticle(..))")
    public Object lockAdvise(ProceedingJoinPoint proceedingJoinPoint) {
        Object value = null;

        createTagToArticleLock.lock();
        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        createTagToArticleLock.unlock();

        return value;
    }
}
