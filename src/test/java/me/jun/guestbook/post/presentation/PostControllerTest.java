package me.jun.guestbook.post.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.post.application.PostService;
import me.jun.guestbook.post.application.PostWriterService;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import me.jun.guestbook.post.application.exception.WriterMismatchException;
import me.jun.guestbook.post.presentation.dto.PostCreateRequest;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import me.jun.guestbook.post.presentation.dto.PostUpdateRequest;
import me.jun.guestbook.post.presentation.dto.PostWriterInfo;
import me.jun.guestbook.security.JwtProvider;
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

import java.lang.reflect.Field;
import java.security.Key;

import static me.jun.guestbook.post.presentation.PostControllerUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @MockBean
    private PostWriterService postWriterService;

    @Autowired
    private JwtProvider jwtProvider;

    private PostResponse postResponse;

    private PostWriterInfo writerInfo;

    private String jwt;

    @BeforeEach
    public void setUp() {
        postResponse = PostResponse.builder()
                .id(1L)
                .title("test title")
                .content("test content")
                .build();

        jwt = jwtProvider.createJwt("testuser@email.com");

        writerInfo = PostWriterInfo.builder()
                .id(1L)
                .name("tset")
                .email("testuser@email.com")
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

        given(postWriterService.retrievePostWriterBy(any()))
                .willReturn(writerInfo);

        mockMvc.perform(post("/api/posts")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(HAL_JSON)
                    .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string("location", POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_SELF_HREF).value(POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_CREATE_POST_HREF).value(POSTS_SELF_URI))
                .andExpect(jsonPath(LINKS_GET_POST_HREF).value(POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_UPDATE_POST_HREF).value(POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_DELETE_POST_HREF).value(POSTS_SELF_URI + "/1"));
    }


    @Test
    void invalidInput_createPostFailTest() throws Exception {
        PostCreateRequest request = PostCreateRequest.builder()
                .title("")
                .content(" ")
                .build();

        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/posts")
                .header(AUTHORIZATION, jwt)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath(STATUS_CODE).exists())
                .andExpect(jsonPath(MESSAGE).exists())
                .andExpect(jsonPath(LINKS_HOME_HREF).exists());
    }

    @Test
    public void readPostTest() throws Exception {
        given(postService.readPost(any()))
                .willReturn(postResponse);

        mockMvc.perform(get("/api/posts/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(LINKS_SELF_HREF).value(POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_CREATE_POST_HREF).value(POSTS_SELF_URI))
                .andExpect(jsonPath(LINKS_GET_POST_HREF).value(POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_UPDATE_POST_HREF).value(POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_DELETE_POST_HREF).value(POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value("test title"))
                .andExpect(jsonPath("content").value("test content"));
    }

    @Test
    void readNoPostFailTest() throws Exception {
        given(postService.readPost(any()))
                .willThrow(new PostNotFoundException());

        mockMvc.perform(get("/api/posts/1"))
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

        given(postWriterService.retrievePostWriterBy(any()))
                .willReturn(writerInfo);

        mockMvc.perform(put("/api/posts")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(HAL_JSON)
                    .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(LINKS_SELF_HREF).value(POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_CREATE_POST_HREF).value(POSTS_SELF_URI))
                .andExpect(jsonPath(LINKS_GET_POST_HREF).value(POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_UPDATE_POST_HREF).value(POSTS_SELF_URI + "/1"))
                .andExpect(jsonPath(LINKS_DELETE_POST_HREF).value(POSTS_SELF_URI + "/1"))
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

        mockMvc.perform(put("/api/posts")
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

        mockMvc.perform(put("/api/posts")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void invalidInput_updatePostFailTest() throws Exception {
        PostUpdateRequest request = PostUpdateRequest.builder()
                .id(-123L)
                .title("")
                .content(" ")
                .build();

        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/api/posts")
                .header(AUTHORIZATION, jwt)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                    .andExpect(jsonPath(STATUS_CODE).exists())
                .andExpect(jsonPath(MESSAGE).exists())
                .andExpect(jsonPath(LINKS_HOME_HREF).exists());
    }

    @Test
    public void deletePostTest() throws Exception {
        given(postService.deletePost(any(), any()))
                .willReturn(1L);

        given(postWriterService.retrievePostWriterBy(any()))
                .willReturn(writerInfo);

        mockMvc.perform(delete("/api/posts/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(HAL_JSON)
                .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(LINKS_SELF_HREF).value(POSTS_SELF_URI))
                .andExpect(jsonPath(LINKS_CREATE_POST_HREF).value(POSTS_SELF_URI));
    }

    @Test
    void noPost_deletePostFailTest() throws Exception {
        doThrow(PostNotFoundException.class)
                .when(postService)
                .deletePost(any(), any());

        mockMvc.perform(delete("/api/posts/2"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void guestMisMatch_deletePostFailTest() throws Exception {
        doThrow(WriterMismatchException.class)
                .when(postService)
                .deletePost(any(), any());

        mockMvc.perform(delete("/api/posts/1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    private Key getKey() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> resolver = Class.forName("me.jun.guestbook.security.JwtProvider");
        Field secret_key = resolver.getDeclaredField("SECRET_KEY");
        secret_key.setAccessible(true);
        return (Key) secret_key.get(Key.class);
    }
}