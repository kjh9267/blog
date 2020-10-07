package me.jun.guestbook.exception;

public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException() {
        super("Email already exists");
    }
}
