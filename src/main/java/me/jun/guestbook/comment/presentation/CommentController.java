package me.jun.guestbook.comment.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.application.CommentService;
import me.jun.guestbook.comment.presentation.dto.CommentCreateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.comment.presentation.dto.CommentUpdateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentWriterInfo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static me.jun.guestbook.utils.EntityModelUtils.commentEntityModel;
import static me.jun.guestbook.utils.RelUtils.*;
import static me.jun.guestbook.utils.SelfUriUtils.commentSelfUri;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<EntityModel<CommentResponse>>
    createComment(@RequestBody CommentCreateRequest request,
                  @CommentWriter CommentWriterInfo writer) {
        CommentResponse commentResponse = commentService.createComment(request, writer.getId());

        URI selfUri = commentSelfUri(commentResponse);

        return ResponseEntity.created(selfUri)
                .body(commentEntityModel(commentResponse));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<EntityModel<CommentResponse>>
        readComment(@PathVariable @Valid Long commentId) {
        CommentResponse commentResponse = commentService.retrieveComment(commentId);

        return ResponseEntity.ok()
                .body(commentEntityModel(commentResponse));
    }

    @PutMapping
    public ResponseEntity<EntityModel<CommentResponse>>
        updateComment(@RequestBody CommentUpdateRequest request,
                      @CommentWriter CommentWriterInfo writerInfo) {
        CommentResponse commentResponse = commentService.updateComment(request, writerInfo.getId());

        return ResponseEntity.ok()
                .body(commentEntityModel(commentResponse));
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<RepresentationModel>
    deleteComment(@PathVariable Long commentId,
                  @CommentWriter CommentWriterInfo writerInfo) {
        commentService.deleteComment(commentId, writerInfo.getId());
        return ResponseEntity.ok()
                .body(commentEntityModel());
    }
}
