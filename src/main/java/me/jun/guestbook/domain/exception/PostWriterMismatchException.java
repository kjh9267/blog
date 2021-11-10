package me.jun.guestbook.domain.exception;

public class PostWriterMismatchException extends RuntimeException {

    public PostWriterMismatchException(String message) {
        super(message);
    }
}
