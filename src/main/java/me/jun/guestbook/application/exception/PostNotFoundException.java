package me.jun.guestbook.application.exception;


import me.jun.support.exception.BusinessException;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException() {
        super("no post");
    }
}
