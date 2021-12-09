package me.jun.blog.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.ArticleService;
import me.jun.blog.application.dto.ArticleCreateRequest;
import me.jun.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.application.dto.PagedArticleResponse;
import me.jun.member.application.dto.MemberInfo;
import me.jun.support.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/blog/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody @Valid ArticleCreateRequest request,
                                                               @Member MemberInfo articleWriterInfo) {

        ArticleResponse response = articleService.createArticle(request, articleWriterInfo.getEmail());

        return ResponseEntity.ok()
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
                                                         @Member MemberInfo articleWriterInfo) {

        ArticleResponse response = articleService.updateArticleInfo(request);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/query")
    public HttpEntity<PagedArticleResponse> queryArticles(@RequestParam("page") int page,
                                                          @RequestParam("size") int size) {
        PagedArticleResponse response = articleService.queryArticles(PageRequest.of(page, size));

        return ResponseEntity.ok()
                .body(response);
    }
}
