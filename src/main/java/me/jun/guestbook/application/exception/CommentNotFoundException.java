package me.jun.guestbook.application.exception;

import me.jun.support.exception.BusinessException;

public class CommentNotFoundException extends BusinessException {
    public CommentNotFoundException() {
        super("no comment");
    }
}
