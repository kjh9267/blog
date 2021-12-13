package me.jun.member.application.exception;

import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.MEMBER_NOT_FOUND;

public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException(String email) {
        super(MEMBER_NOT_FOUND, MEMBER_NOT_FOUND.errorMessageOf(email));
    }
}
