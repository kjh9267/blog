package me.jun.guestbook.post.presentaion;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.dto.PostRequest;
import me.jun.guestbook.dto.PostResponse;
import me.jun.guestbook.post.application.PostNotFoundException;
import me.jun.guestbook.post.application.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void createPostTest() throws Exception {
        PostRequest requestDto = PostRequest.builder()
                .title("my title")
                .content("my content")
                .build();

        String content = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/post")
                    .content(content)
                    .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/index"))
                .andDo(print());
    }

    @Test
    public void readPostTest() throws Exception {
        given(postService.readPost(any()))
                .willReturn(PostResponse.builder().build());

        mockMvc.perform(get("/post/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void readNoPostFailTest() throws Exception {
        given(postService.readPost(any()))
                .willThrow(new PostNotFoundException());

        mockMvc.perform(get("/post/1"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void deletePostTest() throws Exception {
        postService.createPost(PostRequest.builder()
                .title("my title")
                .content("my content")
                .build(), 1L);

        mockMvc.perform(delete("/post/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Disabled
    @Test
    public void updatePostTest() throws Exception {
        postService.createPost(PostRequest.builder()
                .title("my title")
                .content("my content")
                .build(), 1L);

        String content = objectMapper.writeValueAsString(PostRequest.builder()
                .id(1L)
                .title("new title")
                .content("new content")
                .build());

        mockMvc.perform(put("/post/1")
                    .content(content)
                    .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
