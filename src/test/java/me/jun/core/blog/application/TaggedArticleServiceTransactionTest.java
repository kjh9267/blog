package me.jun.core.blog.application;

import me.jun.core.blog.TaggedArticleFixture;
import me.jun.core.blog.application.dto.AddTagRequest;
import me.jun.core.blog.domain.repository.TaggedArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TaggedArticleServiceTransactionTest {

    @Autowired
    private TaggedArticleService taggedArticleService;

    @Autowired
    private TaggedArticleRepository taggedArticleRepository;

    @Test
    void createTagToArticleTest() throws InterruptedException {
        int expected = 2_000;
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (long thread = 0; thread < 1_000; thread++) {
            for (int tagName = 0; tagName < 2; tagName++) {
                AddTagRequest request = TaggedArticleFixture.addTagRequest().toBuilder()
                        .articleId(thread)
                        .tagName(String.valueOf(tagName))
                        .build();

                executorService.submit(() -> taggedArticleService.createTagToArticle(request));
            }
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        assertThat(taggedArticleRepository.findAll().size())
                .isEqualTo(expected);
    }
}