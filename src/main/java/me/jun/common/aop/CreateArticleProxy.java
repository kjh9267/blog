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
public class CreateArticleProxy {

    @Autowired
    @Qualifier("createArticleLock")
    private Lock createArticleLock;

    @Around("execution(* me.jun.core.blog.application.ArticleService.createArticle(..))")
    public Object lockAdvise(ProceedingJoinPoint proceedingJoinPoint) {
        Object value = null;

        createArticleLock.lock();
        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        createArticleLock.unlock();

        return value;
    }
}
