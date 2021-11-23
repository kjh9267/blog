package me.jun.blog.domain.service;

import me.jun.blog.domain.Article;
import me.jun.blog.domain.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMatchingService {

    public void newMatch(Article article, Category category) {
        article.updateCategory(category.getId());
        category.plusMappedArticleCount();
    }

    public void changeMatch(Article article, Category newCategory, Category oldCategory) {
        article.updateCategory(newCategory.getId());
        oldCategory.minusMappedArticleCount();
        newCategory.plusMappedArticleCount();
    }
}
