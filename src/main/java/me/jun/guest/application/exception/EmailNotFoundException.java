package me.jun.guest.application.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
        super("No Email");
    }
}
