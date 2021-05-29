package me.jun.guestbook.post.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.post.application.PostService;
import me.jun.guestbook.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequest request) {
        postService.createPost(request, 1L);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<PostResponse> readPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.readPost(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/post")
    public ResponseEntity<Void> updatePost(@RequestBody PostUpdateRequest requestDto) {
        postService.updatePost(requestDto, 1L);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/post")
    public ResponseEntity<Void> deletePost(@RequestBody Long postId) {
        postService.deletePost(postId, 1L);
        return ResponseEntity.ok().build();
    }
}
