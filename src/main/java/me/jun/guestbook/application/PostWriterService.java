package me.jun.guestbook.application;

import me.jun.guestbook.application.dto.PostWriterInfo;

import java.util.concurrent.CompletableFuture;

public interface PostWriterService {
    CompletableFuture<PostWriterInfo> retrievePostWriterBy(String email);
}
