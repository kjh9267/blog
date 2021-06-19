package me.jun.guestbook.comment.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.comment.application.CommentService;
import me.jun.guestbook.comment.presentation.dto.CommentCreateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static me.jun.guestbook.utils.ControllerTestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    private String jwt;

    private CommentCreateRequest commentCreateRequest;

    private CommentResponse commentResponse;

    @BeforeEach
    void setUp() {
        jwt = jwtProvider.createJwt("testuser@email.com");

        commentResponse = CommentResponse.builder()
                .id(1L)
                .writerId(1L)
                .postId(1L)
                .content("test content")
                .build();
    }

    @Test
    void createCommentTest() throws Exception {
        commentCreateRequest = CommentCreateRequest.builder()
                .postId(1L)
                .content("test content")
                .build();

        String content = objectMapper.writeValueAsString(commentCreateRequest);

        given(commentService.createComment(any(), any()))
                .willReturn(commentResponse);

        mockMvc.perform(post("/api/comments")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(HAL_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(header().string("location", COMMENTS_SELF_URI + "/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(LINKS_SELF_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_CREATE_COMMENT_HREF).value(COMMENTS_SELF_URI))
                .andExpect(jsonPath(LINKS_GET_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_UPDATE_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_DELETE_COMMENT_HREF).value(COMMENTS_SELF_URI + "/1"));
    }
}
