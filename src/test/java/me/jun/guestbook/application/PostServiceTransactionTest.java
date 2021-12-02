package me.jun.guestbook.application;

import me.jun.guestbook.domain.PostCount;
import me.jun.guestbook.domain.repository.PostCountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static me.jun.guestbook.PostFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PostServiceTransactionTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostCountRepository postCountRepository;

    @Test
    void concurrent_updatePostCountTest() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        postService.createPost(postCreateRequest(), WRITER_EMAIL);

        for (int thread = 0; thread < 9; thread++) {
            executorService.execute(
                    () -> {
                        for (int request = 0; request < 10; request++) {
                            postService.retrievePost(POST_ID);
                        }
                    }
            );
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        PostCount postCount = postCountRepository.findByPostId(POST_ID)
                .get();

        assertThat(postCount.getHits().getValue())
                .isEqualTo(90L);
    }
}