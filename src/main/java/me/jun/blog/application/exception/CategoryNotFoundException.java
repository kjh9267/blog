package me.jun.blog.application.exception;

import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.CATEGORY_NOT_FOUND;

public class CategoryNotFoundException extends BusinessException {

    public CategoryNotFoundException(Long id) {
        super(CATEGORY_NOT_FOUND, CATEGORY_NOT_FOUND.errorMessageOf(id));
    }
}
