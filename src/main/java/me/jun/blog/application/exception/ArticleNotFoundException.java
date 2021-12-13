package me.jun.blog.application.exception;

import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.ARTICLE_NOT_FOUND;

public class ArticleNotFoundException extends BusinessException {

    public ArticleNotFoundException(Long id) {
        super(ARTICLE_NOT_FOUND, ARTICLE_NOT_FOUND.errorMessageOf(id));
    }
}
