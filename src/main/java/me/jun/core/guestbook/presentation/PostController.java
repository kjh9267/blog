package me.jun.core.guestbook.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.common.Member;
import me.jun.common.hateoas.LinkCreator;
import me.jun.core.guestbook.application.PostService;
import me.jun.core.guestbook.application.dto.*;
import me.jun.core.member.application.dto.MemberInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/guestbook/posts")
public class PostController {

    private final PostService postService;

    private final LinkCreator linkCreator;

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PostResponse> createPost(
            @RequestBody @Valid PostCreateRequest request,
            @Member MemberInfo writer
    ) {

        request = request.toBuilder()
                .writerId(writer.getId())
                .build();

        PostResponse response = postService.createPost(request);

        linkCreator.createLink(getClass(), response);

        URI uri = WebMvcLinkBuilder.linkTo(getClass())
                .withRel(String.valueOf(response.getId()))
                .toUri();

        return ResponseEntity.created(uri)
                .body(response);
    }

    @GetMapping(
            value = "/{postId}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PostResponse> retrievePost(@PathVariable Long postId) {

        PostResponse response = postService.retrievePost(postId);

        linkCreator.createLink(getClass(), response);

        return ResponseEntity.ok()
                .body(response);
    }

    @PutMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PostResponse> updatePost(
            @RequestBody @Valid PostUpdateRequest request,
            @Member MemberInfo writer
    ) {
        request = request.toBuilder()
                .writerId(writer.getId())
                .build();

        PostResponse response = postService.updatePost(request);

        linkCreator.createLink(getClass(), response);

        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping(
            value = "/{postId}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @Member MemberInfo writer
    ) {

        postService.deletePost(PostDeleteRequest.of(postId, writer.getId()));

        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping(
            value = "/query",
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<PagedPostsResponse> queryPosts(@RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {

        PagedPostsResponse response = postService.queryPosts(PageRequest.of(page, size));

        return ResponseEntity.ok()
                .body(response);
    }
}
