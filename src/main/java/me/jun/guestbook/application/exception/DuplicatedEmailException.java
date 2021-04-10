package me.jun.guestbook.application.exception;

public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException(Throwable cause) {
        super("Email already exists", cause);
    }
}
