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

    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deletePost(@ModelAttribute PostCreateRequest id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/post")
    public ResponseEntity<Void> updatePost(@RequestBody PostCreateRequest requestDto,
                             Model model) {
        PostResponse postResponse = postService.updatePost(requestDto);
        model.addAttribute("post", postResponse);

        return ResponseEntity.ok().build();
    }
}
