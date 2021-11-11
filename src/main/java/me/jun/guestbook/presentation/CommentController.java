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

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/comments")
public class CommentController {

    private final CommentService commentService;

    private final CommentEntityModelCreator entityModelCreator;

    @PostMapping
    public ResponseEntity<EntityModel<CommentResponse>>
    createComment(@RequestBody @Valid CommentCreateRequest request,
                  @CommentWriter CommentWriterInfo writer) {
        CommentResponse commentResponse = commentService.createComment(request, writer.getId());

        URI selfUri = createSelfUri(commentResponse);

        return ResponseEntity.created(selfUri)
                .body(entityModelCreator.createEntityModel(commentResponse));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<EntityModel<CommentResponse>>
    retrieveComment(@PathVariable @Valid Long commentId) {
        CommentResponse commentResponse = commentService.retrieveComment(commentId);

        return ResponseEntity.ok()
                .body(entityModelCreator.createEntityModel(commentResponse));
    }

    @PutMapping
    public ResponseEntity<EntityModel<CommentResponse>>
        updateComment(@RequestBody @Valid CommentUpdateRequest request,
                      @CommentWriter CommentWriterInfo writerInfo) {
        CommentResponse commentResponse = commentService.updateComment(request, writerInfo.getId());

        return ResponseEntity.ok()
                .body(entityModelCreator.createEntityModel(commentResponse));
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<RepresentationModel>
    deleteComment(@PathVariable Long commentId,
                  @CommentWriter CommentWriterInfo writerInfo) {
        commentService.deleteComment(commentId, writerInfo.getId());
        return ResponseEntity.ok()
                .body(entityModelCreator.createHyperlinks());
    }

    @GetMapping("/query/post-id/{postId}")
    ResponseEntity<CollectionModel<EntityModel<CommentResponse>>>
    queryCommentsByPostId(@PathVariable Long postId,
                          @PathParam("page") Integer page,
                          @PathParam("size") Integer size) {
        PagedCommentsResponse response = commentService.queryCommentsByPostId(postId, PageRequest.of(page, size));
        return ResponseEntity.ok()
                .body(entityModelCreator.createCollectionModel(response));
    }

    private URI createSelfUri(CommentResponse commentResponse) {
        return linkTo(getClass())
                .slash(commentResponse.getId())
                .withSelfRel()
                .toUri();
    }
}
