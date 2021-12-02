package me.jun.guestbook.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.common.security.JwtProvider;
import me.jun.guestbook.application.CommentService;
import me.jun.guestbook.application.dto.PagedCommentsResponse;
import me.jun.guestbook.domain.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static me.jun.guestbook.CommentFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private String jwt;

    @BeforeEach
    void setUp() {
        jwt = jwtProvider.createJwt("testuser@email.com");
    }

    @Test
    void createCommentTest() throws Exception {
        String content = objectMapper.writeValueAsString(commentCreateRequest());

        given(commentService.createComment(any(), any()))
                .willReturn(commentResponse());

        mockMvc.perform(post("/api/guestbook/comments")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").value(COMMENT_ID))
                .andExpect(jsonPath("post_id").value(POST_ID))
                .andExpect(jsonPath("content").value(CONTENT));
    }

    @Test
    void retrieveCommentTest() throws Exception {
        given(commentService.retrieveComment(any()))
                .willReturn(commentResponse());

        mockMvc.perform(get("/api/guestbook/comments/1")
                        .accept(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").value(COMMENT_ID))
                .andExpect(jsonPath("post_id").value(POST_ID))
                .andExpect(jsonPath("content").value(CONTENT))
                .andDo(print());
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
                        .header(HttpHeaders.AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").value(COMMENT_ID))
                .andExpect(jsonPath("post_id").value(POST_ID))
                .andExpect(jsonPath("content").value(CONTENT));
    }

    @Test
    void deleteCommentTest() throws Exception {
        given(commentService.deleteComment(any(), any()))
                .willReturn(COMMENT_ID);

        mockMvc.perform(delete("/api/guestbook/comments/1")
                        .header(HttpHeaders.AUTHORIZATION, jwt)
                        .accept(APPLICATION_JSON))
                .andDo(print());

        verify(commentService).deleteComment(any(), any());
    }

    @Test
    void queryCommentsByPostIdTest() throws Exception {
        PagedCommentsResponse response = PagedCommentsResponse.from(new PageImpl<Comment>(
                Arrays.asList(comment())));

        given(commentService.queryCommentsByPostId(any(), any()))
                .willReturn(response);

        mockMvc.perform(get("/api/guestbook/comments/query/post-id/1?page=1&size=10")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}