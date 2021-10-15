package me.jun.guestbook.post.domain;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static me.jun.guestbook.post.PostFixture.hits;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HitsTest {

    @Test
    void constructorTest() {
        assertThat(new Hits()).isInstanceOf(Hits.class);
    }

    @Test
    void constructorTest2() {
        Hits expected = hits();

        assertThat(new Hits(1L)).isEqualToComparingFieldByField(expected);
    }

    @Test
    void updateTest() {
        Hits hits = hits();

        assertThat(hits.update()).isEqualToComparingFieldByField(new Hits(2L));
    }

    @Test
    void ConcurrentUpdateTest() throws InterruptedException {
        final Hits[] hits = {hits()};

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(() -> {
            for (int i = 0; i < 100; i++) {
                hits[0] = hits[0].update();
            }
        });

        executorService.execute(() -> {
            for (int i = 0; i < 100; i++) {
                hits[0] = hits[0].update();
            }
        });

        executorService.awaitTermination(3, TimeUnit.SECONDS);

        assertThat(hits[0].getValue()).isEqualTo(201L);
    }
}