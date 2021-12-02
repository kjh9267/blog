package me.jun.member.application.exception;

import me.jun.support.exception.BusinessException;

public class EmailNotFoundException extends BusinessException {
    public EmailNotFoundException() {
        super("no email");
    }
}
