package me.jun.member.application.exception;

import me.jun.support.exception.BusinessException;

public class DuplicatedEmailException extends BusinessException {

    public DuplicatedEmailException(Throwable cause) {
        super("email already exists", cause);
    }
}
