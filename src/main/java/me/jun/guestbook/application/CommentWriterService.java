package me.jun.guestbook.application;

import me.jun.guestbook.application.dto.CommentWriterInfo;

import java.util.concurrent.CompletableFuture;

public interface CommentWriterService {

    CompletableFuture<CommentWriterInfo> retrieveCommentWriterBy(String email);
}
