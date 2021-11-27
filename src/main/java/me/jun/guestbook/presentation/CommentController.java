package me.jun.guestbook.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.CommentService;
import me.jun.guestbook.application.dto.*;
import me.jun.guestbook.presentation.hateoas.CommentEntityModelCreator;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Mono<CommentResponse>> createComment(@RequestBody @Valid CommentCreateRequest request,
                                                               @CommentWriter CommentWriterInfo writer) {
        Mono<CommentResponse> commentResponseMono = Mono.fromCompletionStage(
                commentService.createComment(request, writer.getId())
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
                      @CommentWriter CommentWriterInfo writerInfo) {
        Mono<CommentResponse> commentResponseMono = Mono.fromCompletionStage(
                commentService.updateComment(request, writerInfo.getId())
        ).log();

        return ResponseEntity.ok()
                .body(commentResponseMono);
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<Mono<Long>> deleteComment(@PathVariable Long commentId,
                                             @CommentWriter CommentWriterInfo writerInfo) {
        Mono<Long> longMono = Mono.fromCompletionStage(
                commentService.deleteComment(commentId, writerInfo.getId())
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
