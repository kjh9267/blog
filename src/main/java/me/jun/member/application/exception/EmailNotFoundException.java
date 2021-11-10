package me.jun.member.application.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
        super("No Email");
    }
}
