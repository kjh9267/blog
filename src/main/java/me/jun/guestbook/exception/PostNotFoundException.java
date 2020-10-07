package me.jun.guestbook.exception;


public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("No Post");
    }
}
