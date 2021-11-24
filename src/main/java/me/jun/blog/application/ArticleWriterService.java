package me.jun.blog.application;

import me.jun.blog.application.dto.ArticleWriterInfo;

public interface ArticleWriterService {

    ArticleWriterInfo retrieveArticleWriter(String email);
}
