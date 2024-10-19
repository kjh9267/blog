package me.jun.core.guestbook;

import me.jun.core.guestbook.application.PostService;
import me.jun.core.guestbook.domain.Post;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static me.jun.core.guestbook.PostFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Component
class CachingTest {

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
        postService.createPost(postCreateRequest());
        postService.retrievePost(POST_ID);

        postService.updatePost(postUpdateRequest());

        Post afterUpdate = (Post) cacheManager.getCache("postStore")
                .get(POST_ID)
                .get();

        assertThat(afterUpdate.getContent()).isEqualTo(NEW_CONTENT);
    }

    @Test
    void deleteCacheTest() {
        postService.createPost(postCreateRequest());
        postService.retrievePost(POST_ID);

        postService.deletePost(postDeleteRequest());

        assertThrows(
                NullPointerException.class,
                () -> cacheManager.getCache("postStore")
                        .get(POST_ID)
                        .get()
        );
    }

    @Test
    void deleteAllCacheTest() {
        for (int request = 0; request < 10; request++) {
            postService.createPost(postCreateRequest());
        }
        postService.retrievePost(POST_ID);

        postService.deletePostByWriterId(POST_WRITER_ID);

        assertThrows(
                NullPointerException.class,
                () -> cacheManager.getCache("postStore")
                        .get(POST_ID)
                        .get()
        );
    }
}