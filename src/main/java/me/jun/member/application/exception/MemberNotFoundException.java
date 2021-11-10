package me.jun.member.application.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("There is no account");
    }
}
