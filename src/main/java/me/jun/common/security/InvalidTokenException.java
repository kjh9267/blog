package me.jun.common.security;

import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.INVALID_TOKEN;

public class InvalidTokenException extends BusinessException {

    public InvalidTokenException() {
        super(INVALID_TOKEN, INVALID_TOKEN.getMessage());
    }
}
