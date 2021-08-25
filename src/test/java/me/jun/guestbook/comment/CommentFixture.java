package me.jun.guestbook.comment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.guestbook.comment.domain.Comment;
import me.jun.guestbook.comment.domain.CommentWriter;
import me.jun.guestbook.comment.presentation.dto.CommentCreateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.comment.presentation.dto.CommentUpdateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentWriterInfo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CommentFixture {
    public static final String LINKS_SELF_HREF = "_links.self.href";

    public static final String LINKS_CREATE_COMMENT_HREF = "_links.create_comment.href";

    public static final String LINKS_GET_COMMENT_HREF = "_links.get_comment.href";

    public static final String LINKS_UPDATE_COMMENT_HREF = "_links.update_comment.href";

    public static final String LINKS_DELETE_COMMENT_HREF = "_links.delete_comment.href";

    public static final String QUERY_COMMENTS_BY_POST_HREF = "_links.query_comments_by_post_id.href";

    public static final String COMMENTS_SELF_URI = "http://localhost/api/comments";

    public static final String QUERY_COMMENTS_BY_POST_URI = "http://localhost/api/comments/query/post-id";

    public static final Long COMMENT_ID = 1L;

    public static final Long POST_ID = 1L;

    public static final Long WRITER_ID = 1L;

    public static final String COMMENT_NAME = "test user";

    public static final String CONTENT = "test content";

    private static final String EMAIL = "testuser@email.com";

    public static CommentWriter commentWriter() {
        return new CommentWriter(WRITER_ID);
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
                .content(CONTENT)
                .build();
    }

    public static CommentResponse commentResponse() {
        return CommentResponse.builder()
                .id(COMMENT_ID)
                .postId(POST_ID)
                .content(CONTENT)
                .build();
    }

    public static CommentWriterInfo commentWriterInfo() {
        return CommentWriterInfo.builder()
                .id(WRITER_ID)
                .name(COMMENT_NAME)
                .email(EMAIL)
                .build();
    }
}
