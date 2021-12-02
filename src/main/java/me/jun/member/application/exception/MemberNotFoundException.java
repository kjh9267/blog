package me.jun.member.application.exception;

import me.jun.support.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException() {
        super("no member");
    }
}
