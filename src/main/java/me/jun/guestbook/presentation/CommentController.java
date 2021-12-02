package me.jun.guestbook.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.CommentService;
import me.jun.guestbook.application.dto.CommentCreateRequest;
import me.jun.guestbook.application.dto.CommentResponse;
import me.jun.guestbook.application.dto.CommentUpdateRequest;
import me.jun.guestbook.application.dto.PagedCommentsResponse;
import me.jun.member.application.dto.MemberInfo;
import me.jun.support.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/guestbook/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid CommentCreateRequest request,
                                                               @Member MemberInfo writer) {

        CommentResponse response = commentService.createComment(request, writer.getEmail());

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> retrieveComment(@PathVariable Long commentId) {

        CommentResponse response = commentService.retrieveComment(commentId);

        return ResponseEntity.ok()
                .body(response);
    }

    @PutMapping
    public ResponseEntity<CommentResponse>
        updateComment(@RequestBody @Valid CommentUpdateRequest request,
                      @Member MemberInfo writerInfo) {

        CommentResponse response = commentService.updateComment(request, writerInfo.getEmail());

        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<Long> deleteComment(@PathVariable Long commentId,
                                             @Member MemberInfo writerInfo) {

        Long response = commentService.deleteComment(commentId, writerInfo.getEmail());

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/query/post-id/{postId}")
    ResponseEntity<PagedCommentsResponse> queryCommentsByPostId(@PathVariable Long postId,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        PagedCommentsResponse response = commentService.queryCommentsByPostId(postId, PageRequest.of(page, size));
        return ResponseEntity.ok()
                .body(response);
    }
}
