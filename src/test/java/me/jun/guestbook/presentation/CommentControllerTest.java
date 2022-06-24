package me.jun.guestbook.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.common.security.JwtProvider;
import me.jun.guestbook.application.CommentService;
import me.jun.guestbook.application.dto.CommentCreateRequest;
import me.jun.guestbook.application.dto.PagedCommentsResponse;
import me.jun.guestbook.application.exception.CommentNotFoundException;
import me.jun.member.application.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static me.jun.guestbook.CommentFixture.*;
import static me.jun.member.MemberFixture.memberResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private MemberService memberService;

    private String jwt;

    @BeforeEach
    void setUp() {
        jwt = jwtProvider.createJwt("testuser@email.com");

        given(memberService.retrieveMemberBy(any()))
                .willReturn(memberResponse());
    }

    @Test
    void createCommentTest() throws Exception {
        String content = objectMapper.writeValueAsString(commentCreateRequest());

        given(commentService.createComment(any(), any()))
                .willReturn(commentResponse());

        ResultActions resultActions = mockMvc.perform(post("/api/guestbook/comments")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, jwt))
                .andDo(print());

        expectedJson(resultActions);
    }

    @Test
    void retrieveCommentTest() throws Exception {
        given(commentService.retrieveComment(any()))
                .willReturn(commentResponse());

        ResultActions resultActions = mockMvc.perform(get("/api/guestbook/comments/1")
                        .accept(APPLICATION_JSON))
                .andDo(print());

        expectedJson(resultActions);
    }

    @Test
    void noComment_retrieveCommentFailTest() throws Exception {
        given(commentService.retrieveComment(any()))
                .willThrow(new CommentNotFoundException(COMMENT_ID));

        mockMvc.perform(get("/api/guestbook/comments/1")
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateCommentTest() throws Exception {
        String content = objectMapper.writeValueAsString(commentUpdateRequest());

        given(commentService.updateComment(any(), any()))
                .willReturn(commentResponse());

        mockMvc.perform(put("/api/guestbook/comments")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links").exists())
                .andExpect(jsonPath("id").value(COMMENT_ID))
                .andExpect(jsonPath("post_id").value(POST_ID))
                .andExpect(jsonPath("content").value(CONTENT));
    }

    @Test
    void noComment_updateCommentFailTest() throws Exception {
        given(commentService.deleteComment(any(), any()))
                .willThrow(new CommentNotFoundException(COMMENT_ID));

        mockMvc.perform(delete("/api/guestbook/comments/1")
                .header(AUTHORIZATION, jwt)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void deleteCommentTest() throws Exception {
        given(commentService.deleteComment(any(), any()))
                .willReturn(COMMENT_ID);

        mockMvc.perform(delete("/api/guestbook/comments/1")
                        .header(AUTHORIZATION, jwt)
                        .accept(APPLICATION_JSON))
                .andDo(print());

        verify(commentService).deleteComment(any(), any());
    }

    @Test
    void noComment_deleteCommentFailTest() throws Exception {
        given(commentService.deleteComment(any(), any()))
                .willThrow(new CommentNotFoundException(COMMENT_ID));

        mockMvc.perform(delete("/api/guestbook/comments/2")
                .header(AUTHORIZATION, jwt)
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void queryCommentsByPostIdTest() throws Exception {
        PagedCommentsResponse response = pagedCommentsResponse();

        String expected = objectMapper.writeValueAsString(response);

        given(commentService.queryCommentsByPostId(any(), any()))
                .willReturn(response);

        mockMvc.perform(get("/api/guestbook/comments/query/post-id/1?page=1&size=10")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));
    }

    @Test
    void invalidInputTest() throws Exception {
        String content = objectMapper.writeValueAsString(
                CommentCreateRequest.builder()
                        .postId(-1L)
                        .content(" ")
                        .build()
        );

        mockMvc.perform(post("/api/guestbook/comments")
                .header(AUTHORIZATION, jwt)
                .contentType(APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}