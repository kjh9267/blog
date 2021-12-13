package me.jun.support.exception;

import lombok.Getter;
import me.jun.common.error.ErrorCode;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    private final String message;

    public BusinessException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
