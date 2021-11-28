package me.jun.guestbook.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.CommentService;
import me.jun.guestbook.application.dto.*;
import me.jun.member.application.dto.MemberInfo;
import me.jun.support.Member;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Mono<CommentResponse>> createComment(@RequestBody @Valid CommentCreateRequest request,
                                                               @Member MemberInfo writer) {
        Mono<CommentResponse> commentResponseMono = Mono.fromCompletionStage(
                commentService.createComment(request, writer.getEmail())
        ).log();

        return ResponseEntity.ok()
                .body(commentResponseMono);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Mono<CommentResponse>> retrieveComment(@PathVariable Long commentId) {
        Mono<CommentResponse> commentResponseMono = Mono.fromCompletionStage(
                commentService.retrieveComment(commentId)
        ).log();

        return ResponseEntity.ok()
                .body(commentResponseMono);
    }

    @PutMapping
    public ResponseEntity<Mono<CommentResponse>>
        updateComment(@RequestBody @Valid CommentUpdateRequest request,
                      @Member MemberInfo writerInfo) {
        Mono<CommentResponse> commentResponseMono = Mono.fromCompletionStage(
                commentService.updateComment(request, writerInfo.getEmail())
        ).log();

        return ResponseEntity.ok()
                .body(commentResponseMono);
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<Mono<Long>> deleteComment(@PathVariable Long commentId,
                                             @Member MemberInfo writerInfo) {
        Mono<Long> longMono = Mono.fromCompletionStage(
                commentService.deleteComment(commentId, writerInfo.getEmail())
        ).log();
        return ResponseEntity.ok()
                .body(longMono);
    }

    @GetMapping("/query/post-id/{postId}")
    ResponseEntity<CommentResponse> queryCommentsByPostId(@PathVariable Long postId,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        PagedCommentsResponse response = commentService.queryCommentsByPostId(postId, PageRequest.of(page, size));
        return null;
    }
}
