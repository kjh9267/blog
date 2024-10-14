package me.jun.core.blog.application;

import me.jun.core.blog.application.dto.ArticleCreateRequest;
import me.jun.core.blog.domain.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static me.jun.core.blog.ArticleFixture.articleCreateRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class ArticleServiceTransactionTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void createArticleTest() throws InterruptedException {
        int expected = 2_000;
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int thread = 0; thread < 1_000; thread++) {
            for (int count = 0; count < 2; count++) {
                ArticleCreateRequest request = articleCreateRequest().toBuilder()
                        .categoryName(String.valueOf(thread))
                        .build();

                executorService.submit(() -> articleService.createArticle(request));
            }
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        assertThat(articleRepository.findAll().size())
                .isEqualTo(expected);
    }
}