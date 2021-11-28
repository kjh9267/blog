package me.jun.guestbook;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.guestbook.application.dto.CommentCreateRequest;
import me.jun.guestbook.application.dto.CommentResponse;
import me.jun.guestbook.application.dto.CommentUpdateRequest;
import me.jun.guestbook.domain.Comment;
import me.jun.guestbook.domain.CommentWriter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CommentFixture {

    public static final Long COMMENT_ID = 1L;

    public static final Long POST_ID = 1L;

    public static final String CONTENT = "test content";

    public static final String NEW_CONTENT = "new content";

    public static final String EMAIL = "testuser@email.com";

    public static CommentWriter commentWriter() {
        return new CommentWriter(EMAIL);
    }

    public static Comment comment() {
        return Comment.builder()
                .id(COMMENT_ID)
                .postId(POST_ID)
                .commentWriter(commentWriter())
                .content(CONTENT)
                .build();
    }

    public static CommentCreateRequest commentCreateRequest() {
        return CommentCreateRequest.builder()
                .postId(POST_ID)
                .content(CONTENT)
                .build();
    }

    public static CommentUpdateRequest commentUpdateRequest() {
        return CommentUpdateRequest.builder()
                .id(COMMENT_ID)
                .postId(POST_ID)
                .content(NEW_CONTENT)
                .build();
    }

    public static CommentResponse commentResponse() {
        return CommentResponse.builder()
                .id(COMMENT_ID)
                .postId(POST_ID)
                .content(CONTENT)
                .build();
    }

    public static CommentResponse updatedCommentResponse() {
        return CommentResponse.builder()
                .id(COMMENT_ID)
                .postId(POST_ID)
                .content(NEW_CONTENT)
                .build();
    }
}
