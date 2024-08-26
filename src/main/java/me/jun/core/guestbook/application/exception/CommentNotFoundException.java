package me.jun.core.guestbook.application.exception;

import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.COMMENT_NOT_FOUND;

public class CommentNotFoundException extends BusinessException {

    public CommentNotFoundException(Long id) {
        super(COMMENT_NOT_FOUND, COMMENT_NOT_FOUND.errorMessageOf(id));
    }
}
