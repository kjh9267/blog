package me.jun.guestbook.application.exception;

import me.jun.support.exception.BusinessException;

public class PostCountNotFoundException extends BusinessException {
    public PostCountNotFoundException() {
        super("no post count");
    }
}
