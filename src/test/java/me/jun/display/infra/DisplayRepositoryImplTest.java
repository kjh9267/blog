package me.jun.display.infra;

import me.jun.blog.application.ArticleService;
import me.jun.blog.application.dto.ArticleCreateRequest;
import me.jun.display.domain.repository.DisplayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static me.jun.blog.ArticleFixture.articleCreateRequest;
import static me.jun.member.MemberFixture.ADMIN_EMAIL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@SpringBootTest
class DisplayRepositoryImplTest {

    @Autowired
    private DisplayRepository displayRepositoryImpl;

    @Autowired
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        for (long id = 0; id < 10; id++) {
            ArticleCreateRequest request = articleCreateRequest();
            articleService.createArticle(request, ADMIN_EMAIL);
        }
    }

    @Test
    void retrieveDisplayTest() {
        Page page = displayRepositoryImpl.retrieveDisplay(0, 10);

        assertAll(
                () -> assertThat(page.getTotalElements())
                        .isEqualTo(10),
                () -> assertThat(page.getTotalPages())
                        .isEqualTo(1)
        );
    }

    @Test
    void retrieveDisplayFailTest() {
        Page page = displayRepositoryImpl.retrieveDisplay(1, 10);

        assertAll(
                () -> assertThat(page.getTotalElements())
                        .isEqualTo(0),
                () -> assertThat(page.getTotalPages())
                        .isEqualTo(1)
        );
    }
}