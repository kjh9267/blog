package me.jun.core.blog.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.common.Member;
import me.jun.common.hateoas.LinkCreator;
import me.jun.core.blog.application.ArticleService;
import me.jun.core.blog.application.dto.ArticleCreateRequest;
import me.jun.core.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.core.blog.application.dto.ArticleResponse;
import me.jun.core.blog.application.dto.PagedArticleResponse;
import me.jun.core.member.application.dto.MemberInfo;
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

    private final LinkCreator linkCreator;

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody @Valid ArticleCreateRequest request,
                                                               @Member MemberInfo articleWriterInfo) {

        request = request.toBuilder()
                .writerId(articleWriterInfo.getId())
                .build();

        ArticleResponse response = articleService.createArticle(request);

        linkCreator.createLink(getClass(), response);

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

        linkCreator.createLink(getClass(), response);

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

        linkCreator.createLink(getClass(), response);

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
