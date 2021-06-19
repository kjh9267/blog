package me.jun.guestbook.comment.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.application.CommentService;
import me.jun.guestbook.comment.presentation.dto.CommentCreateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.comment.presentation.dto.CommentWriterInfo;
import me.jun.guestbook.post.presentation.PostController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<EntityModel<CommentResponse>>
    createComment(@RequestBody CommentCreateRequest request,
                  @CommentWriter CommentWriterInfo writer) {
        CommentResponse comment = commentService.createComment(request, writer.getId());

        WebMvcLinkBuilder selfLinkBuilder = linkTo(CommentController.class)
                .slash(comment.getId());
        URI selfUri = selfLinkBuilder
                .withSelfRel()
                .toUri();

        return ResponseEntity.created(selfUri)
                .body(
                        EntityModel.of(comment)
                                .add(selfLinkBuilder.withSelfRel())
                                .add(selfLinkBuilder.withRel("get_comment"))
                                .add(selfLinkBuilder.withRel("update_comment"))
                                .add(selfLinkBuilder.withRel("delete_comment"))
                );
    }

}
