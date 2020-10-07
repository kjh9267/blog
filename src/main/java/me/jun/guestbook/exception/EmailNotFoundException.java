package me.jun.guestbook.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
        super("No Email");
    }
}
