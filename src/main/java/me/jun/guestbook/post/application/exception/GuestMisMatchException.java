package me.jun.guestbook.post.application.exception;

public class GuestMisMatchException extends RuntimeException {

    public GuestMisMatchException(String message) {
        super(message);
    }
}
