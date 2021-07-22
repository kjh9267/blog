package me.jun.guestbook.post.domain;

public class PostWriterMismatchException extends RuntimeException {

    public PostWriterMismatchException(String message) {
        super(message);
    }
}
