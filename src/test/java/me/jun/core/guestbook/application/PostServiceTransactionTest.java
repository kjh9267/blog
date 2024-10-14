package me.jun.core.guestbook.application;

import me.jun.core.guestbook.domain.PostCount;
import me.jun.core.guestbook.domain.repository.PostCountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static me.jun.core.guestbook.PostFixture.POST_ID;
import static me.jun.core.guestbook.PostFixture.postCreateRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class PostServiceTransactionTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostCountRepository postCountRepository;

    @Test
    void concurrent_updatePostCountTest() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        postService.createPost(postCreateRequest());

        for (int thread = 0; thread < 200; thread++) {
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
                .isEqualTo(2000L);
    }
}