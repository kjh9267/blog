package me.jun.common.aop;

import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Transactional
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Order(10)
public @interface CreateArticleLockBeforeTransaction {
}
