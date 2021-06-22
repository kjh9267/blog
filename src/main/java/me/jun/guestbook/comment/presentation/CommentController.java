package me.jun.guestbook.comment.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.application.CommentService;
import me.jun.guestbook.comment.presentation.dto.CommentCreateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.comment.presentation.dto.CommentUpdateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentWriterInfo;
import me.jun.guestbook.common.EntityModelCreator;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Qualifier("commentEntityModelCreator")
    private final EntityModelCreator<CommentResponse> entityModelCreator;

    @PostMapping
    public ResponseEntity<EntityModel<CommentResponse>>
    createComment(@RequestBody CommentCreateRequest request,
                  @CommentWriter CommentWriterInfo writer) {
        CommentResponse commentResponse = commentService.createComment(request, writer.getId());

        URI selfUri = createSelfUri(commentResponse);

        return ResponseEntity.created(selfUri)
                .body(entityModelCreator.createEntityModel(commentResponse, getClass()));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<EntityModel<CommentResponse>>
        readComment(@PathVariable @Valid Long commentId) {
        CommentResponse commentResponse = commentService.retrieveComment(commentId);

        return ResponseEntity.ok()
                .body(entityModelCreator.createEntityModel(commentResponse, getClass()));
    }

    @PutMapping
    public ResponseEntity<EntityModel<CommentResponse>>
        updateComment(@RequestBody CommentUpdateRequest request,
                      @CommentWriter CommentWriterInfo writerInfo) {
        CommentResponse commentResponse = commentService.updateComment(request, writerInfo.getId());

        return ResponseEntity.ok()
                .body(entityModelCreator.createEntityModel(commentResponse, getClass()));
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<RepresentationModel>
    deleteComment(@PathVariable Long commentId,
                  @CommentWriter CommentWriterInfo writerInfo) {
        commentService.deleteComment(commentId, writerInfo.getId());
        return ResponseEntity.ok()
                .body(entityModelCreator.createEntityModel(getClass()));
    }

    private URI createSelfUri(CommentResponse commentResponse) {
        return linkTo(getClass())
                .slash(commentResponse.getId())
                .withSelfRel()
                .toUri();
    }
}
