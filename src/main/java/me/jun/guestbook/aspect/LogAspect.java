package me.jun.guestbook.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Around("execution(* me.jun.guestbook.domain.post.TempPostRepository.*(..))")
    public Object executionTimeLogAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        Logger logger = LoggerFactory.getLogger("Data access time Logger");
        Object value = null;
        long start = System.currentTimeMillis();

        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        logger.info("execution time: " + ( end - start ) / 1000.0 +"sec");
        return value;
    }
}
