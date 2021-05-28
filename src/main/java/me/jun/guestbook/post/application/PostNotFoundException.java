package me.jun.guestbook.post.application;


public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("No Post");
    }
}
