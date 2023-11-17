package me.jun.guestbook.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.common.hateoas.LinkCreator;
import me.jun.guestbook.application.PostService;
import me.jun.guestbook.application.dto.PagedPostsResponse;
import me.jun.guestbook.application.dto.PostCreateRequest;
import me.jun.guestbook.application.dto.PostResponse;
import me.jun.guestbook.application.dto.PostUpdateRequest;
import me.jun.member.application.dto.MemberInfo;
import me.jun.common.Member;
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
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostCreateRequest request,
                                                         @Member MemberInfo writer) {

        PostResponse response = postService.createPost(request, writer.getEmail());

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
    public ResponseEntity<PostResponse> updatePost(@RequestBody @Valid PostUpdateRequest requestDto,
                                                         @Member MemberInfo writer) {

        PostResponse response = postService.updatePost(requestDto, writer.getEmail());

        linkCreator.createLink(getClass(), response);

        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping(
            value = "/{postId}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                                 @Member MemberInfo writer) {

        postService.deletePost(postId, writer.getEmail());

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
