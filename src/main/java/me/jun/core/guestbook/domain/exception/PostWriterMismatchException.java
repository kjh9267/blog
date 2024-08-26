package me.jun.core.guestbook.domain.exception;

import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.MEMBER_MISMATCH;

public class PostWriterMismatchException extends BusinessException {

    public PostWriterMismatchException(String email) {
        super(MEMBER_MISMATCH, MEMBER_MISMATCH.errorMessageOf(email));
    }
}
