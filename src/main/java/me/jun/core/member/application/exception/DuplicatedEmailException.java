package me.jun.core.member.application.exception;

import me.jun.support.exception.BusinessException;

import static me.jun.common.error.ErrorCode.MEMBER_ALREADY_EXIST;

public class DuplicatedEmailException extends BusinessException {

    public DuplicatedEmailException(String email) {
        super(MEMBER_ALREADY_EXIST, MEMBER_ALREADY_EXIST.errorMessageOf(email));
    }
}
