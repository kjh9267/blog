package me.jun.guestbook.application.exception;

public class PostCountNotFoundException extends RuntimeException {

    public PostCountNotFoundException(String message) {
        super(message);
    }
}
