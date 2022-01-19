package me.jun.blog.application.exception;

import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.CATEGORY_ID_NOT_FOUND;
import static me.jun.common.error.ErrorCode.CATEGORY_NAME_NOT_FOUND;

public class CategoryNotFoundException extends BusinessException {

    public CategoryNotFoundException(Long id) {
        super(CATEGORY_ID_NOT_FOUND, CATEGORY_ID_NOT_FOUND.errorMessageOf(id));
    }

    public CategoryNotFoundException(String name) {
        super(CATEGORY_NAME_NOT_FOUND, CATEGORY_NAME_NOT_FOUND.errorMessageOf(name));
    }
}
