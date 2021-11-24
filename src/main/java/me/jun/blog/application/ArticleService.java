package me.jun.blog.application;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.dto.ArticleCreateRequest;
import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.blog.application.dto.ArticleWriterInfo;
import me.jun.blog.application.exception.ArticleNotFoundException;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.Category;
import me.jun.blog.domain.repository.ArticleRepository;
import me.jun.blog.domain.service.CategoryMatchingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final CategoryService categoryService;

    private final CategoryMatchingService categoryMatchingService;

    public ArticleResponse createArticle(ArticleCreateRequest request, ArticleWriterInfo articleWriterInfoId) {
        Article article = request.toArticle()
                .updateWriterId(articleWriterInfoId.getId());

        String categoryName = request.getCategoryName();
        Category category = categoryService.createCategoryOrElseGet(categoryName);

        categoryMatchingService.newMatch(article, category);

        article = articleRepository.save(article);

        return ArticleResponse.from(article);
    }

    @Transactional(readOnly = true)
    public ArticleResponse retrieveArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(ArticleNotFoundException::new);

        return ArticleResponse.from(article);
    }

    public ArticleResponse updateArticleInfo(ArticleInfoUpdateRequest request) {
        Long requestId = request.getId();

        Article updatedArticle = articleRepository.findById(requestId)
                .map(article -> article.updateInfo(
                        request.getTitle(),
                        request.getContent()
                ))
                .orElseThrow(ArticleNotFoundException::new);

        return ArticleResponse.from(updatedArticle);
    }
}
