package me.jun.guestbook.application.exception;


public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("No Post");
    }
}
