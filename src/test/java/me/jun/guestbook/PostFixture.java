package me.jun.guestbook;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.guestbook.application.dto.PagedPostsResponse;
import me.jun.guestbook.application.dto.PostCreateRequest;
import me.jun.guestbook.application.dto.PostResponse;
import me.jun.guestbook.application.dto.PostUpdateRequest;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.domain.PostWriter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class PostFixture {

    public static final String TITLE = "test title";

    public static final String CONTENT = "test content";

    public static final String NEW_TITLE = "new title";

    public static final String NEW_CONTENT = "new content";

    public static final Long POST_ID = 1L;

    public static final String WRITER_EMAIL = "testuser@email.com";

    public static PostWriter postWriter() {
        return new PostWriter(WRITER_EMAIL);
    }


    public static Post post() {
        return Post.builder()
                .id(POST_ID)
                .title(TITLE)
                .content(CONTENT)
                .postWriter(postWriter())
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

    public static PostResponse updatedPostResponse() {
        return PostResponse.of(Post.builder()
                .id(POST_ID)
                .title(NEW_TITLE)
                .content(NEW_CONTENT)
                .build());
    }

    public static PagedPostsResponse pagedPostsResponse() {
        return PagedPostsResponse.from(
                new PageImpl<>(
                        LongStream.range(1, 10)
                                .mapToObj(
                                        id -> post().toBuilder()
                                                .id(id)
                                                .build()
                                )
                                .collect(toList())
                )
        );
    }

    public static PageRequest pageRequest() {
        return PageRequest.of(1, 2);
    }

    public static ResultActions expectedJson(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links").exists())
                .andExpect(jsonPath("id").value(POST_ID))
                .andExpect(jsonPath("title").value(TITLE))
                .andExpect(jsonPath("content").value(CONTENT));
    }
}
