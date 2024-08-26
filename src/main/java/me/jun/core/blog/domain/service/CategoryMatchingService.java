package me.jun.core.blog.domain.service;

import me.jun.core.blog.domain.Article;
import me.jun.core.blog.domain.Category;
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
