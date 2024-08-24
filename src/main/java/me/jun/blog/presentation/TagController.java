package me.jun.blog.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.TaggedArticleService;
import me.jun.blog.application.dto.AddTagRequest;
import me.jun.blog.application.dto.TaggedArticleResponse;
import me.jun.common.hateoas.LinkCreator;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/blog/tag")
@RequiredArgsConstructor
public class TagController {

    private final TaggedArticleService taggedArticleService;

    private final LinkCreator linkCreator;

    @PostMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TaggedArticleResponse> createTag(@RequestBody @Valid AddTagRequest request) {

        TaggedArticleResponse response = taggedArticleService.createTagToArticle(request);

        linkCreator.createLink(getClass(), response);

        URI uri = WebMvcLinkBuilder.linkTo(getClass())
                .withRel(String.valueOf(response.getTagId()))
                .toUri();

        return ResponseEntity.created(uri)
                .body(response);
    }
}
