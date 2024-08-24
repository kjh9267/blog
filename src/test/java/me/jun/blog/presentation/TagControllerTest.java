package me.jun.blog.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.blog.application.TaggedArticleService;
import me.jun.common.security.JwtProvider;
import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static me.jun.blog.ArticleFixture.ARTICLE_ID;
import static me.jun.blog.ArticleFixture.ARTICLE_WRITER_EMAIL;
import static me.jun.blog.TagFixture.TAG_ID;
import static me.jun.blog.TaggedArticleFixture.addTagRequest;
import static me.jun.blog.TaggedArticleFixture.taggedArticleResponse;
import static me.jun.member.MemberFixture.memberResponse;
import static me.jun.member.domain.Role.ADMIN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaggedArticleService taggedArticleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private MemberService memberService;

    private String jwt;

    @BeforeEach
    void setUp() {
        jwt = jwtProvider.createJwt(ARTICLE_WRITER_EMAIL);

        MemberResponse admin = memberResponse().toBuilder()
                .role(ADMIN)
                .build();

        given(memberService.retrieveMemberBy(any()))
                .willReturn(admin);
    }

    @Test
    void createTagTest() throws Exception {
        String content = objectMapper.writeValueAsString(addTagRequest());

        given(taggedArticleService.createTagToArticle(any()))
                .willReturn(taggedArticleResponse());

        mockMvc.perform(post("/api/blog/tag")
                        .header(AUTHORIZATION, jwt)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links").exists())
                .andExpect(jsonPath("article_id").value(ARTICLE_ID))
                .andExpect(jsonPath("tag_id").value(TAG_ID));
    }
}