package me.jun.guestbook.application.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super("There is no account");
    }
}
