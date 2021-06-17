package me.jun.guestbook.post.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.post.application.PostService;
import me.jun.guestbook.post.presentation.dto.PostCreateRequest;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import me.jun.guestbook.post.presentation.dto.PostUpdateRequest;
import me.jun.guestbook.post.presentation.dto.WriterInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequest request,
                                           @Writer WriterInfo writer) {
        postService.createPost(request, writer.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<PostResponse> readPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.readPost(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/post")
    public ResponseEntity<Void> updatePost(@RequestBody PostUpdateRequest requestDto,
                                           @Writer WriterInfo writer) {
        postService.updatePost(requestDto, writer.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                           @Writer WriterInfo writer) {
        postService.deletePost(postId, writer.getId());
        return ResponseEntity.ok().build();
    }
}
