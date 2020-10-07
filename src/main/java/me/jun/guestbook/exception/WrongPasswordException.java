package me.jun.guestbook.exception;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("wrong password");
    }
}
