package me.jun.blog.application;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.dto.ArticleCreateRequest;
import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.blog.application.exception.ArticleNotFoundException;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.Category;
import me.jun.blog.domain.repository.ArticleRepository;
import me.jun.blog.domain.service.CategoryMatchingService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final CategoryService categoryService;

    private final CategoryMatchingService categoryMatchingService;

    @Async
    public CompletableFuture<ArticleResponse> createArticle(ArticleCreateRequest request, String writerEmail) {
        Article article = request.toArticle()
                .updateWriterId(writerEmail);

        String categoryName = request.getCategoryName();
        Category category = categoryService.createCategoryOrElseGet(categoryName);

        categoryMatchingService.newMatch(article, category);

        article = articleRepository.save(article);

        return CompletableFuture.completedFuture(
                ArticleResponse.from(article)
        );
    }

    @Transactional(readOnly = true)
    @Async
    public CompletableFuture<ArticleResponse> retrieveArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(ArticleNotFoundException::new);

        return CompletableFuture.completedFuture(
                ArticleResponse.from(article)
        );
    }

    @Async
    public CompletableFuture<ArticleResponse> updateArticleInfo(ArticleInfoUpdateRequest request) {
        Long requestId = request.getId();

        Article updatedArticle = articleRepository.findById(requestId)
                .map(article -> article.updateInfo(
                        request.getTitle(),
                        request.getContent()
                ))
                .orElseThrow(ArticleNotFoundException::new);

        return CompletableFuture.completedFuture(
                ArticleResponse.from(updatedArticle)
        );
    }
}
