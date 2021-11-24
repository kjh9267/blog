package me.jun.blog.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.ArticleService;
import me.jun.blog.application.dto.ArticleCreateRequest;
import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.blog.application.dto.ArticleWriterInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody @Valid ArticleCreateRequest request,
                                                         @ArticleWriter ArticleWriterInfo articleWriterInfo) {

        ArticleResponse response = articleService.createArticle(request, articleWriterInfo);

        URI uri = linkTo(getClass()).slash(response.getArticleId()).toUri();

        return ResponseEntity.created(uri)
                .body(response);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> retrieveArticle(@PathVariable Long articleId) {

        ArticleResponse response = articleService.retrieveArticle(articleId);

        return ResponseEntity.ok()
                .body(response);
    }

    @PutMapping
    public ResponseEntity<ArticleResponse> updateArticle(@RequestBody @Valid ArticleInfoUpdateRequest request,
                                                         @ArticleWriter ArticleWriterInfo articleWriterInfo) {
        ArticleResponse response = articleService.updateArticleInfo(request);

        return ResponseEntity.ok()
                .body(response);
    }
}
