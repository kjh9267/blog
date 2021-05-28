package me.jun.guestbook.domain.guest;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("wrong password");
    }
}
