package me.jun.member.domain.exception;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("wrong password");
    }
}
