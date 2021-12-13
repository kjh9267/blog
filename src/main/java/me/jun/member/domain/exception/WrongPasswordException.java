package me.jun.member.domain.exception;

import me.jun.common.error.ErrorCode;
import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.INVALID_PASSWORD;

public class WrongPasswordException extends BusinessException {

    public WrongPasswordException() {
        super(INVALID_PASSWORD, INVALID_PASSWORD.getMessage());
    }
}
