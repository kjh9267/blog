package me.jun.guestbook.post.application.exception;


public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("No Post");
    }
}
