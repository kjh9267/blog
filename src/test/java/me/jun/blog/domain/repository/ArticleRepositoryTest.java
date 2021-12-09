package me.jun.blog.domain.repository;

import me.jun.blog.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;

import static me.jun.blog.ArticleFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ArticleRepositoryTest {

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

        assertThat(createdAt).isBefore(modifiedAt);
    }

    @Test
    void findAllTest() {
        Article article = article().toBuilder()
                .id(null)
                .build();

        for (int count = 0; count < 10; count++) {
            articleRepository.save(article);
        }

        Page<Article> all = articleRepository.findAll(PageRequest.of(0, 10));

        assertThat(all.getSize())
                .isEqualTo(10);
    }
}