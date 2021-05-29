package me.jun.guestbook.post.presentaion;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.dto.PostCreateRequest;
import me.jun.guestbook.dto.PostResponse;
import me.jun.guestbook.dto.PostUpdateRequest;
import me.jun.guestbook.post.application.PostService;
import me.jun.guestbook.post.application.exception.GuestMisMatchException;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
        PostCreateRequest request = PostCreateRequest.builder()
                .title("test title")
                .content("test content")
                .build();

        String content = objectMapper.writeValueAsString(request);

        doNothing().when(postService);

        mockMvc.perform(post("/post")
                    .content(content)
                    .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
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

    @Disabled
    @Test
    public void updatePostTest() throws Exception {
        PostUpdateRequest request = PostUpdateRequest.builder()
                .id(1L)
                .title("new title")
                .content("new content")
                .build();

        String content = objectMapper.writeValueAsString(request);

        doNothing().when(postService)
                .updatePost(any(), anyLong());

        mockMvc.perform(put("/post")
                .content(content)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void noPost_updatePostFailTest() throws Exception {
        PostUpdateRequest request = PostUpdateRequest.builder()
                .id(2L)
                .title("new title")
                .content("new content")
                .build();

        String content = objectMapper.writeValueAsString(request);

        doThrow(PostNotFoundException.class)
                .when(postService)
                .updatePost(any(), anyLong());

        mockMvc.perform(put("/post")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void guestMisMatch_updatePostFailTest() throws Exception {
        PostUpdateRequest request = PostUpdateRequest.builder()
                .id(1L)
                .title("new title")
                .content("new content")
                .build();

        String content = objectMapper.writeValueAsString(request);

        doThrow(GuestMisMatchException.class)
                .when(postService)
                .updatePost(any(), anyLong());

        mockMvc.perform(put("/post")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deletePostTest() throws Exception {
        doNothing().when(postService).deletePost(any(), anyLong());

        mockMvc.perform(delete("/post/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void noPost_deletePostFailTest() throws Exception {
        doThrow(PostNotFoundException.class)
                .when(postService)
                .deletePost(any(), anyLong());

        mockMvc.perform(delete("/post/2"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void guestMisMatch_deletePostFailTest() throws Exception {
        doThrow(GuestMisMatchException.class)
                .when(postService)
                .deletePost(any(), anyLong());

        mockMvc.perform(delete("/post/1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
