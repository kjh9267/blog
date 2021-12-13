package me.jun.guestbook.domain.exception;

import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.MEMBER_MISMATCH;

public class CommentWriterMismatchException extends BusinessException {

    public CommentWriterMismatchException(String email) {
        super(MEMBER_MISMATCH, MEMBER_MISMATCH.errorMessageOf(email));
    }
}
