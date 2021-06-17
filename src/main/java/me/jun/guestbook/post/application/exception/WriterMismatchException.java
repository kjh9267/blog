package me.jun.guestbook.post.application.exception;

public class WriterMismatchException extends RuntimeException {

    public WriterMismatchException(String message) {
        super(message);
    }
}
