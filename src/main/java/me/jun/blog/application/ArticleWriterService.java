package me.jun.blog.application;

import me.jun.blog.application.dto.ArticleWriterInfo;

import java.util.concurrent.CompletableFuture;

public interface ArticleWriterService {

    CompletableFuture<ArticleWriterInfo> retrieveArticleWriter(String email);
}
