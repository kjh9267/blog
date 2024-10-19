package me.jun.core.blog.application;

import lombok.RequiredArgsConstructor;
import me.jun.core.blog.application.dto.ArticleCreateRequest;
import me.jun.core.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.core.blog.application.dto.ArticleResponse;
import me.jun.core.blog.application.dto.PagedArticleResponse;
import me.jun.core.blog.application.exception.ArticleNotFoundException;
import me.jun.core.blog.domain.Article;
import me.jun.core.blog.domain.Category;
import me.jun.core.blog.domain.repository.ArticleRepository;
import me.jun.core.blog.domain.service.CategoryMatchingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final CategoryService categoryService;

    private final CategoryMatchingService categoryMatchingService;

    @Transactional
    public ArticleResponse createArticle(ArticleCreateRequest request) {
        Article article = request.toArticle()
                .updateWriterId(request.getWriterId());

        String categoryName = request.getCategoryName();

        Category category = categoryService.createCategoryOrElseGet(categoryName);

        categoryMatchingService.newMatch(article, category);

        article = articleRepository.save(article);

        return ArticleResponse.from(article);
    }

    @Transactional(readOnly = true)
    public ArticleResponse retrieveArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        return ArticleResponse.from(article);
    }

    @Transactional
    public ArticleResponse updateArticleInfo(ArticleInfoUpdateRequest request) {
        Long requestId = request.getArticleId();

        Article updatedArticle = articleRepository.findById(requestId)
                .map(article -> article.updateInfo(
                        request.getNewTitle(),
                        request.getNewContent()
                ))
                .orElseThrow(() -> new ArticleNotFoundException(requestId));

        return ArticleResponse.from(updatedArticle);
    }

    @Transactional(readOnly = true)
    public PagedArticleResponse queryArticles(PageRequest request) {
        Page<Article> articles = articleRepository.findAll(request);

        return PagedArticleResponse.from(articles);
    }
}
