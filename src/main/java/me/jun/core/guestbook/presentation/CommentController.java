package me.jun.core.guestbook.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.common.Member;
import me.jun.common.hateoas.LinkCreator;
import me.jun.core.guestbook.application.CommentService;
import me.jun.core.guestbook.application.dto.CommentCreateRequest;
import me.jun.core.guestbook.application.dto.CommentResponse;
import me.jun.core.guestbook.application.dto.CommentUpdateRequest;
import me.jun.core.guestbook.application.dto.PagedCommentsResponse;
import me.jun.core.member.application.dto.MemberInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/guestbook/comments")
public class CommentController {

    private final CommentService commentService;

    private final LinkCreator linkCreator;

    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody @Valid CommentCreateRequest request,
            @Member MemberInfo writer
    ) {
        request = request.toBuilder()
                .writerId(writer.getId())
                .build();

        CommentResponse response = commentService.createComment(request);

        linkCreator.createLink(getClass(), response);

        URI uri = WebMvcLinkBuilder.linkTo(getClass())
                .withRel(String.valueOf(response.getId()))
                .toUri();

        return ResponseEntity.created(uri)
                .body(response);
    }

    @GetMapping(
            value = "/{commentId}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommentResponse> retrieveComment(@PathVariable Long commentId) {

        CommentResponse response = commentService.retrieveComment(commentId);

        linkCreator.createLink(getClass(), response);

        return ResponseEntity.ok()
                .body(response);
    }

    @PutMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommentResponse>
        updateComment(
                @RequestBody @Valid CommentUpdateRequest request,
                @Member MemberInfo writerInfo
    ) {
        request = request.toBuilder()
                .writerId(writerInfo.getId())
                .build();

        CommentResponse response = commentService.updateComment(request);

        linkCreator.createLink(getClass(), response);

        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping(
            value = "/{commentId}",
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @Member MemberInfo writerInfo
    ) {

        commentService.deleteComment(commentId, writerInfo.getId());

        return ResponseEntity.noContent()
                .build();
    }

    @GetMapping(
            value = "/query/post-id/{postId}",
            produces = APPLICATION_JSON_VALUE
    )
    ResponseEntity<PagedCommentsResponse> queryCommentsByPostId(@PathVariable Long postId,
                                                          @RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size) {
        PagedCommentsResponse response = commentService.queryCommentsByPostId(postId, PageRequest.of(page, size));

        return ResponseEntity.ok()
                .body(response);
    }
}
