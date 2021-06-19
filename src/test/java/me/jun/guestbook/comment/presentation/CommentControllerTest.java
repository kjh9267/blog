package me.jun.guestbook.comment.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Ignore
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

    private CommentCreateRequest commentCreateRequest;

    private CommentResponse commentResponse;

    @BeforeEach
    void setUp() {
        commentResponse = CommentResponse.builder()
                .id(1L)
                .writerId(1L)
                .postId(1L)
                .content("test content")
                .build();
    }

    @Test
    void createCommentTest() throws Exception {
        CommentCreateRequest commentCreateRequest = this.commentCreateRequest.builder()
                .postId(1L)
                .content("test content")
                .build();

        String content = objectMapper.writeValueAsString(commentCreateRequest);

        mockMvc.perform(post("/api/post")
                    .content(content)
                    .contentType(JSON)
                    .accept(HAL_JSON))
                .andDo(print());
    }
}
