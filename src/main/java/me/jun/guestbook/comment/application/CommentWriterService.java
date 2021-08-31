package me.jun.guestbook.comment.application;

import me.jun.guestbook.comment.application.dto.CommentWriterInfo;

public interface CommentWriterService {

    CommentWriterInfo retrieveCommentWriterBy(String email);
}
