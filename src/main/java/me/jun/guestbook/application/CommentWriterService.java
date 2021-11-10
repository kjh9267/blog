package me.jun.guestbook.application;

import me.jun.guestbook.application.dto.CommentWriterInfo;

public interface CommentWriterService {

    CommentWriterInfo retrieveCommentWriterBy(String email);
}
