package me.jun.blog.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.ArticleService;
import me.jun.blog.application.dto.ArticleCreateRequest;
import me.jun.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.application.dto.ArticleWriterInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Mono<ArticleResponse>> createArticle(@RequestBody @Valid ArticleCreateRequest request,
                                                               @ArticleWriter ArticleWriterInfo articleWriterInfo) {

        Mono<ArticleResponse> articleResponseMono = Mono.fromCompletionStage(
                articleService.createArticle(request, articleWriterInfo)
        ).log();

        return ResponseEntity.ok()
                .body(articleResponseMono);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Mono<ArticleResponse>> retrieveArticle(@PathVariable Long articleId) {

        Mono<ArticleResponse> articleResponseMono = Mono.fromCompletionStage(
                articleService.retrieveArticle(articleId)
        ).log();

        return ResponseEntity.ok()
                .body(articleResponseMono);
    }

    @PutMapping
    public ResponseEntity<Mono<ArticleResponse>> updateArticle(@RequestBody @Valid ArticleInfoUpdateRequest request,
                                                         @ArticleWriter ArticleWriterInfo articleWriterInfo) {
        Mono<ArticleResponse> articleResponseMono = Mono.fromCompletionStage(
                articleService.updateArticleInfo(request)
        ).log();

        return ResponseEntity.ok()
                .body(articleResponseMono);
    }
}
