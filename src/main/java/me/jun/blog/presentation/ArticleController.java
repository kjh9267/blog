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
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/blog/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody @Valid ArticleCreateRequest request,
                                                               @Member MemberInfo articleWriterInfo) {

        ArticleResponse response = articleService.createArticle(request, articleWriterInfo.getEmail());

        URI uri = WebMvcLinkBuilder.linkTo(getClass())
                .withRel(String.valueOf(response.getArticleId()))
                .toUri();

        return ResponseEntity.created(uri)
                .body(response);
    }

    @GetMapping(
            value = "/{articleId}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArticleResponse> retrieveArticle(@PathVariable Long articleId) {

        ArticleResponse response = articleService.retrieveArticle(articleId);

        return ResponseEntity.ok()
                .body(response);
    }

    @PutMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArticleResponse> updateArticle(@RequestBody @Valid ArticleInfoUpdateRequest request,
                                                         @Member MemberInfo articleWriterInfo) {

        ArticleResponse response = articleService.updateArticleInfo(request);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping(
            value = "/query",
            produces = APPLICATION_JSON_VALUE
    )
    public HttpEntity<PagedArticleResponse> queryArticles(@RequestParam("page") int page,
                                                          @RequestParam("size") int size) {
        PagedArticleResponse response = articleService.queryArticles(PageRequest.of(page, size));

        return ResponseEntity.ok()
                .body(response);
    }
}
