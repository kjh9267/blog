package me.jun.blog.application.exception;

import me.jun.support.exception.BusinessException;

public class CategoryNotFoundException extends BusinessException {
    public CategoryNotFoundException() {
        super("no category");
    }
}
