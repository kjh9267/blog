package me.jun.core.blog.domain.repository;

import me.jun.core.blog.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;

import static me.jun.core.blog.ArticleFixture.*;
import static me.jun.core.blog.CategoryFixture.CATEGORY_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class ArticleRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void createdAtTest() {
        Article article = article();
        article = articleRepository.save(article);

        Instant now = Instant.now();

        assertThat(article.getCreatedAt())
                .isBeforeOrEqualTo(now);
    }

    @Test
    void modifiedAtTest() {
        Article article = article();
        article = articleRepository.save(article);

        Instant createdAt = article.getCreatedAt();

        article.updateInfo(NEW_TITLE, NEW_CONTENT);

        entityManager.flush();

        Instant modifiedAt = article.getModifiedAt();

        assertThat(createdAt)
                .isBeforeOrEqualTo(modifiedAt);
    }

    @Test
    void findAllTest() {
        Article article = article().toBuilder()
                .id(null)
                .build();

        for (int count = 0; count < 30; count++) {
            articleRepository.save(article);
        }

        Page<Article> all = articleRepository.findAll(PageRequest.of(2, 10));

        assertThat(all.getSize())
                .isEqualTo(10);
    }

    @Test
    void findAllByCategoryIdTest() {
        for (long id = 1; id <= 30; id++) {
            articleRepository.save(
                    article().toBuilder()
                            .id(id)
                            .build()
            );
        }

        Page<Article> articles = articleRepository.findByCategoryId(CATEGORY_ID, PageRequest.of(2, 10));

        assertThat(articles.getSize())
                .isEqualTo(10);
    }
}
