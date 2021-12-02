package me.jun.blog.application.exception;

import me.jun.support.exception.BusinessException;

public class ArticleNotFoundException extends BusinessException {
    public ArticleNotFoundException() {
        super("no article");
    }
}
