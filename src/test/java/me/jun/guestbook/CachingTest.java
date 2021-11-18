package me.jun.guestbook;

import me.jun.guestbook.application.PostService;
import me.jun.guestbook.domain.Post;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;

import static me.jun.guestbook.PostFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Component
public class CachingTest {

    @Autowired
    private SlowPostRepository slowPostRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void cachingTest() {
        Date date = new Date();
        long start = date.getTime();

        for (int request = 0; request < 100; request++) {
            slowPostRepository.findById(POST_ID);
        }

        long end = new Date().getTime();
        long delay = (end - start) / 1_000;

        assertThat(delay)
                .isCloseTo(2L, Offset.offset(1L));
    }

    @Test
    void updateCacheTest() {
        postService.createPost(postCreateRequest(), WRITER_ID);
        postService.retrievePost(POST_ID);

        postService.updatePost(postUpdateRequest(), WRITER_ID);

        Post afterUpdate = (Post) cacheManager.getCache("posts")
                .get(POST_ID)
                .get();

        assertThat(afterUpdate.getContent()).isEqualTo(NEW_CONTENT);
    }

    @Test
    void deleteCacheTest() {
        postService.createPost(postCreateRequest(), WRITER_ID);
        postService.retrievePost(POST_ID);

        postService.deletePost(POST_ID, WRITER_ID);

        assertThrows(
                NullPointerException.class,
                () -> cacheManager.getCache("posts")
                        .get(POST_ID)
                        .get()
        );
    }

    @Test
    void deleteAllCacheTest() {
        for (int request = 0; request < 10; request++) {
            postService.createPost(postCreateRequest(), WRITER_ID);
        }
        postService.retrievePost(POST_ID);

        postService.deletePostByWriterId(WRITER_ID);

        assertThrows(
                NullPointerException.class,
                () -> cacheManager.getCache("posts")
                        .get(POST_ID)
                        .get()
        );
    }
}
