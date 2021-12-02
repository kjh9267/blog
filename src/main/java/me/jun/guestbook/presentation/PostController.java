package me.jun.guestbook.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.guestbook.application.PostCountService;
import me.jun.guestbook.application.PostService;
import me.jun.guestbook.application.dto.PagedPostsResponse;
import me.jun.guestbook.application.dto.PostCreateRequest;
import me.jun.guestbook.application.dto.PostResponse;
import me.jun.guestbook.application.dto.PostUpdateRequest;
import me.jun.member.application.dto.MemberInfo;
import me.jun.support.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/guestbook/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostCreateRequest request,
                                                         @Member MemberInfo writer) {

        PostResponse response = postService.createPost(request, writer.getEmail());

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> retrievePost(@PathVariable Long postId) {

        PostResponse response = postService.retrievePost(postId);

        return ResponseEntity.ok()
                .body(response);
    }

    @PutMapping
    public ResponseEntity<PostResponse> updatePost(@RequestBody @Valid PostUpdateRequest requestDto,
                                                         @Member MemberInfo writer) {

        PostResponse response = postService.updatePost(requestDto, writer.getEmail());

        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Long> deletePost(@PathVariable Long postId,
                                                 @Member MemberInfo writer) {

        Long response = postService.deletePost(postId, writer.getEmail());

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/query")
    ResponseEntity<PagedPostsResponse> queryPosts(@RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {

        PagedPostsResponse response = postService.queryPosts(PageRequest.of(page, size));

        return ResponseEntity.ok()
                .body(response);
    }
}
