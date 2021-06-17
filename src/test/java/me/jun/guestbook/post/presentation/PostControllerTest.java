package me.jun.guestbook.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.post.presentation.dto.PostCreateRequest;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import me.jun.guestbook.post.presentation.dto.PostUpdateRequest;
import me.jun.guestbook.post.application.PostService;
import me.jun.guestbook.post.application.exception.WriterMismatchException;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private PostResponse postResponse;

    @BeforeEach
    public void setUp() {
        postResponse = PostResponse.builder()
                .id(1L)
                .title("test title")
                .content("test content")
                .build();
    }

    @Test
    public void createPostTest() throws Exception {
        PostCreateRequest request = PostCreateRequest.builder()
                .title("test title")
                .content("test content")
                .build();

        String content = objectMapper.writeValueAsString(request);

        given(postService.createPost(any(), any()))
                .willReturn(postResponse);

        mockMvc.perform(post("/api/post")
                    .content(content)
                    .contentType("application/json")
                    .accept("application/hal+json"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("location", "http://localhost/api/post/1"))
                .andExpect(jsonPath("_links.self.href").value("http://localhost/api/post/1"))
                .andExpect(jsonPath("_links.get_post.href").value("http://localhost/api/post/1"))
                .andExpect(jsonPath("_links.update_post.href").value("http://localhost/api/post/1"))
                .andExpect(jsonPath("_links.delete_post.href").value("http://localhost/api/post/1"));
    }

    @Test
    public void readPostTest() throws Exception {
        given(postService.readPost(any()))
                .willReturn(postResponse);

        mockMvc.perform(get("/api/post/1")
                    .contentType("application/json")
                    .accept("application/hal+json"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links.self.href").value("http://localhost/api/post/1"))
                .andExpect(jsonPath("_links.create_post.href").value("http://localhost/api/post"))
                .andExpect(jsonPath("_links.update_post.href").value("http://localhost/api/post/1"))
                .andExpect(jsonPath("_links.delete_post.href").value("http://localhost/api/post/1"))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value("test title"))
                .andExpect(jsonPath("content").value("test content"));
    }

    @Test
    void readNoPostFailTest() throws Exception {
        given(postService.readPost(any()))
                .willThrow(new PostNotFoundException());

        mockMvc.perform(get("/api/post/1"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void updatePostTest() throws Exception {
        PostUpdateRequest request = PostUpdateRequest.builder()
                .id(1L)
                .title("test title")
                .content("test content")
                .build();

        String content = objectMapper.writeValueAsString(request);

        given(postService.updatePost(any(), any()))
                .willReturn(postResponse);

        mockMvc.perform(put("/api/post")
                    .content(content)
                    .contentType("application/json")
                    .accept("application/hal+json"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links.self.href").value("http://localhost/api/post/1"))
                .andExpect(jsonPath("_links.create_post.href").value("http://localhost/api/post"))
                .andExpect(jsonPath("_links.get_post.href").value("http://localhost/api/post/1"))
                .andExpect(jsonPath("_links.delete_post.href").value("http://localhost/api/post/1"))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value("test title"))
                .andExpect(jsonPath("content").value("test content"));
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
                .updatePost(any(), any());

        mockMvc.perform(put("/api/post")
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

        doThrow(WriterMismatchException.class)
                .when(postService)
                .updatePost(any(), any());

        mockMvc.perform(put("/api/post")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deletePostTest() throws Exception {
        given(postService.deletePost(any(), any()))
                .willReturn(1L);

        mockMvc.perform(delete("/api/post/1")
                    .contentType("application/json")
                    .accept("application/hal+json"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links.self.href").value("http://localhost/api/post"))
                .andExpect(jsonPath("_links.create_post.href").value("http://localhost/api/post"));
    }

    @Test
    void noPost_deletePostFailTest() throws Exception {
        doThrow(PostNotFoundException.class)
                .when(postService)
                .deletePost(any(), any());

        mockMvc.perform(delete("/api/post/2"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void guestMisMatch_deletePostFailTest() throws Exception {
        doThrow(WriterMismatchException.class)
                .when(postService)
                .deletePost(any(), any());

        mockMvc.perform(delete("/api/post/1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
