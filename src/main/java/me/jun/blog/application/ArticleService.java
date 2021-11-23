package me.jun.blog.application;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.dto.ArticleCreateRequest;
import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.application.dto.ArticleUpdateRequest;
import me.jun.blog.application.exception.ArticleNotFoundException;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResponse createArticle(ArticleCreateRequest request) {
        Article article = request.toArticle();
        article = articleRepository.save(article);

        return ArticleResponse.from(article);
    }

    @Transactional(readOnly = true)
    public ArticleResponse retrieveArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(ArticleNotFoundException::new);

        return ArticleResponse.from(article);
    }

    public ArticleResponse updateArticle(ArticleUpdateRequest request) {
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
