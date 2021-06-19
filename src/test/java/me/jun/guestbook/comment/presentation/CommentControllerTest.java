package me.jun.guestbook.comment.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.comment.application.CommentService;
import me.jun.guestbook.comment.presentation.dto.CommentCreateRequest;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.security.JwtProvider;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CommentControllerTest {
    private static final String HAL_JSON = "application/hal+json";
    private static final String JSON = "application/json";

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

        mockMvc.perform(post("/api/comment")
                        .content(content)
                        .contentType(JSON)
                        .accept(HAL_JSON)
                        .header(HttpHeaders.AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(header().string("location", "http://localhost/api/comment/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links.self.href").value("http://localhost/api/comment/1"))
                .andExpect(jsonPath("_links.get_comment.href").value("http://localhost/api/comment/1"))
                .andExpect(jsonPath("_links.update_comment.href").value("http://localhost/api/comment/1"))
                .andExpect(jsonPath("_links.delete_comment.href").value("http://localhost/api/comment/1"));
    }
}
