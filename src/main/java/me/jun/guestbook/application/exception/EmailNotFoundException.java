package me.jun.guestbook.application.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
        super("No Email");
    }
}
