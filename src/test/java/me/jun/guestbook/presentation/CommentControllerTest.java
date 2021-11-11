package me.jun.guestbook.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.application.CommentService;
import me.jun.guestbook.application.CommentWriterService;
import me.jun.guestbook.domain.Comment;
import me.jun.guestbook.application.dto.PagedCommentsResponse;
import me.jun.common.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static me.jun.guestbook.CommentFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentWriterService commentWriterService;

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

        given(commentWriterService.retrieveCommentWriterBy(any()))
                .willReturn(commentWriterInfo());

        mockMvc.perform(post("/api/comments")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(header().string("location", COMMENTS_SELF_URI + "/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").value(COMMENT_ID))
                .andExpect(jsonPath("post_id").value(POST_ID))
                .andExpect(jsonPath("content").value(CONTENT))
                .andExpect(jsonPath(LINKS_SELF_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_CREATE_COMMENT_HREF).value(COMMENTS_SELF_URI))
                .andExpect(jsonPath(LINKS_GET_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_UPDATE_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_DELETE_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"));
    }

    @Test
    void retrieveCommentTest() throws Exception {
        given(commentService.retrieveComment(any()))
                .willReturn(commentResponse());

        mockMvc.perform(get("/api/comments/1")
                    .accept(HAL_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").value(COMMENT_ID))
                .andExpect(jsonPath("post_id").value(POST_ID))
                .andExpect(jsonPath("content").value(CONTENT))
                .andExpect(jsonPath(LINKS_SELF_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_CREATE_COMMENT_HREF).value(COMMENTS_SELF_URI))
                .andExpect(jsonPath(LINKS_GET_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_UPDATE_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_DELETE_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andDo(print());
    }

    @Test
    void updateCommentTest() throws Exception {
        given(commentWriterService.retrieveCommentWriterBy(any()))
                .willReturn(commentWriterInfo());

        String content = objectMapper.writeValueAsString(commentUpdateRequest());

        given(commentService.updateComment(any(), any()))
                .willReturn(commentResponse());

        mockMvc.perform(put("/api/comments")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(HAL_JSON)
                    .header(HttpHeaders.AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").value(COMMENT_ID))
                .andExpect(jsonPath("post_id").value(POST_ID))
                .andExpect(jsonPath("content").value(CONTENT))
                .andExpect(jsonPath(LINKS_SELF_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_CREATE_COMMENT_HREF).value(COMMENTS_SELF_URI))
                .andExpect(jsonPath(LINKS_GET_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_UPDATE_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_DELETE_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"));
    }

    @Test
    void deleteCommentTest() throws Exception {
        given(commentService.deleteComment(any(), any()))
                .willReturn(COMMENT_ID);

        given(commentWriterService.retrieveCommentWriterBy(any()))
                .willReturn(commentWriterInfo());

        mockMvc.perform(delete("/api/comments/1")
                    .header(HttpHeaders.AUTHORIZATION, jwt)
                    .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(LINKS_SELF_HREF).value(COMMENTS_SELF_URI))
                .andExpect(jsonPath(LINKS_CREATE_COMMENT_HREF).value(COMMENTS_SELF_URI));

        verify(commentService).deleteComment(any(), any());
    }

    @Test
    void queryCommentsByPostIdTest() throws Exception {
        PagedCommentsResponse response = PagedCommentsResponse.from(new PageImpl<Comment>(
                Arrays.asList(comment())));

        given(commentService.queryCommentsByPostId(any(), any()))
                .willReturn(response);

        mockMvc.perform(get("/api/comments/query/post-id/1?page=1&size=10")
                    .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(LINKS_SELF_HREF).value(QUERY_COMMENTS_BY_POST_URI))
                .andExpect(jsonPath(LINKS_CREATE_COMMENT_HREF).value(COMMENTS_SELF_URI))
                .andExpect(jsonPath(QUERY_COMMENTS_BY_POST_HREF).value(QUERY_COMMENTS_BY_POST_URI));
    }
}
