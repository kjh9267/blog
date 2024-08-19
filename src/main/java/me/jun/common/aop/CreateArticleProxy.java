package me.jun.common.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

@Aspect
@Component
@RequiredArgsConstructor
@Order(1)
public class CreateArticleProxy {

    private final Lock createArticleLock;

    @Around("execution(* me.jun.blog.application.CategoryService.createCategoryOrElseGet(..))")
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
