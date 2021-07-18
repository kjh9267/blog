package me.jun.guestbook.post;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.guestbook.post.domain.Post;
import me.jun.guestbook.post.presentation.dto.PostCreateRequest;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import me.jun.guestbook.post.presentation.dto.PostUpdateRequest;
import me.jun.guestbook.post.presentation.dto.PostWriterInfo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class PostFixture {
    public static final String LINKS_SELF_HREF = "_links.self.href";

    public static final String LINKS_CREATE_POST_HREF = "_links.create_post.href";

    public static final String LINKS_GET_POST_HREF = "_links.get_post.href";

    public static final String LINKS_UPDATE_POST_HREF = "_links.update_post.href";

    public static final String LINKS_DELETE_POST_HREF = "_links.delete_post.href";

    public static final String POSTS_SELF_URI = "http://localhost/api/posts";

    public static final String LINKS_HOME_HREF = "_links.home.href";

    public static final String STATUS_CODE = "status_code";

    public static final String MESSAGE = "message";

    public static final String TITLE = "test title";

    public static final String CONTENT = "test content";

    public static final String NEW_TITLE = "new title";

    public static final String NEW_CONTENT = "new content";

    public static final Long POST_ID = 1L;

    public static final Long WRITER_ID = 1L;

    public static final String WRITER_NAME = "test user";

    public static final String EMAIL = "testuser@email.com";

    public static Post post() {
        return Post.builder()
                .id(POST_ID)
                .title(TITLE)
                .content(CONTENT)
                .writerId(WRITER_ID)
                .build();
    }

    public static PostCreateRequest postCreateRequest() {
        return PostCreateRequest.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();
    }

    public static PostUpdateRequest postUpdateRequest() {
        return PostUpdateRequest.builder()
                .id(POST_ID)
                .title(NEW_TITLE)
                .content(NEW_CONTENT)
                .build();
    }

    public static PostResponse postResponse() {
        return PostResponse.of(post());
    }

    public static PostWriterInfo postWriterInfo() {
        return PostWriterInfo.builder()
                .id(WRITER_ID)
                .name(WRITER_NAME)
                .email(EMAIL)
                .build();
    }
}
