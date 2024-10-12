package me.jun.core.display.infra;

import me.jun.core.blog.ArticleFixture;
import me.jun.core.blog.application.ArticleService;
import me.jun.core.blog.application.dto.ArticleCreateRequest;
import me.jun.core.display.domain.repository.DisplayRepository;
import me.jun.core.member.application.RegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import static me.jun.core.member.MemberFixture.ADMIN_EMAIL;
import static me.jun.core.member.MemberFixture.adminRegisterRequest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@SpringBootTest
class DisplayRepositoryImplTest {

    @Autowired
    private DisplayRepository displayRepositoryImpl;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RegisterService registerService;

    @Test
    void retrieveDisplayTest() {
        registerService.register(adminRegisterRequest());

        for (long id = 0; id < 10; id++) {
            ArticleCreateRequest request = ArticleFixture.articleCreateRequest();
            articleService.createArticle(request, ADMIN_EMAIL);
        }

        Page page = displayRepositoryImpl.retrieveDisplay(0, 10);

        assertAll(
                () -> assertThat(page.getNumberOfElements())
                        .isEqualTo(10),
                () -> assertThat(page.getTotalPages())
                        .isEqualTo(1)
        );
    }

    @Test
    void retrieveDisplayFailTest() {
        Page page = displayRepositoryImpl.retrieveDisplay(1, 10);

        assertAll(
                () -> assertThat(page.getNumberOfElements())
                        .isZero(),
                () -> assertThat(page.getTotalPages())
                        .isEqualTo(1)
        );
    }
}