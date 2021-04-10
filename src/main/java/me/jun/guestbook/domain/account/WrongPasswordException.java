package me.jun.guestbook.domain.account;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("wrong password");
    }
}
