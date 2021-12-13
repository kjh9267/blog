package me.jun.blog.application;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.application.exception.ArticleNotFoundException;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.Category;
import me.jun.blog.domain.repository.ArticleRepository;
import me.jun.blog.domain.service.CategoryMatchingService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleCategoryService {

    private final ArticleRepository articleRepository;

    private final CategoryService categoryService;

    private final CategoryMatchingService categoryMatchingService;

    public ArticleResponse updateCategoryOfArticle(ArticleInfoUpdateRequest request) {
        Long articleId = request.getId();
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        String newCategoryName = request.getCategoryName();
        Category newCategory = categoryService.createCategoryOrElseGet(newCategoryName);

        Long oldCategoryId = article.getCategoryId();
        Category oldCategory = categoryService.retrieveCategoryById(oldCategoryId);

        categoryMatchingService.changeMatch(article, newCategory, oldCategory);

        return ArticleResponse.from(article);
    }
}
