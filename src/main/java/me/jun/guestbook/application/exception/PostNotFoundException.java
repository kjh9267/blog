package me.jun.guestbook.application.exception;


import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.POST_NOT_FOUND;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException(Long id) {
        super(POST_NOT_FOUND, POST_NOT_FOUND.errorMessageOf(id));
    }
}
