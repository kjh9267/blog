package me.jun.guestbook.post.application.exception;

public class WriterMisMatchException extends RuntimeException {

    public WriterMisMatchException(String message) {
        super(message);
    }
}
