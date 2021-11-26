package me.jun.guestbook.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.PostService;
import me.jun.guestbook.application.dto.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostCreateRequest request,
                                                   @PostWriter PostWriterInfo writer) {
        PostResponse postResponse = postService.createPost(request, writer.getId());

        URI location = URI.create("/api/posts" + postResponse.getId());

        return ResponseEntity.created(location)
                .body(postResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> retrievePost(@PathVariable Long postId) {
        PostResponse postResponse = postService.retrievePost(postId);

        return ResponseEntity.ok()
                .body(postResponse);
    }

    @PutMapping
    public ResponseEntity<PostResponse>
    updatePost(@RequestBody @Valid PostUpdateRequest requestDto,
               @PostWriter PostWriterInfo writer) {
        PostResponse postResponse = postService.updatePost(requestDto, writer.getId());

        return ResponseEntity.ok()
                .body(postResponse);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                           @PostWriter PostWriterInfo writer) {
        postService.deletePost(postId, writer.getId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/query")
    ResponseEntity<PagedPostsResponse> queryPosts(@RequestParam("page") Integer page,
                                            @RequestParam("size") Integer size) {
        PagedPostsResponse response = postService.queryPosts(PageRequest.of(page, size));

        return ResponseEntity.ok()
                .body(response);
    }
}
