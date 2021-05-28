package me.jun.guestbook.guest.domain;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("wrong password");
    }
}
