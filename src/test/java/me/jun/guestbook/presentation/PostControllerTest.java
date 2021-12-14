package me.jun.guestbook.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.common.security.JwtProvider;
import me.jun.guestbook.PostFixture;
import me.jun.guestbook.application.PostService;
import me.jun.guestbook.application.dto.PagedPostsResponse;
import me.jun.guestbook.application.dto.PostCreateRequest;
import me.jun.guestbook.application.dto.PostUpdateRequest;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.domain.exception.PostWriterMismatchException;
import me.jun.member.application.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Arrays;

import static me.jun.guestbook.PostFixture.*;
import static me.jun.member.MemberFixture.memberResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private MemberService memberService;

    private String jwt;

    @BeforeEach
    public void setUp() {
        jwt = jwtProvider.createJwt(WRITER_EMAIL);

        given(memberService.retrieveMemberBy(any()))
                .willReturn(memberResponse());
    }

    @Test
    public void createPostTest() throws Exception {
        String content = objectMapper.writeValueAsString(postCreateRequest());

        given(postService.createPost(any(), any()))
                .willReturn(postResponse());

        mockMvc.perform(post("/api/guestbook/posts")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    void invalidInput_createPostFailTest() throws Exception {
        PostCreateRequest request = PostCreateRequest.builder()
                .title("")
                .content(" ")
                .build();

        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/guestbook/posts")
                        .header(AUTHORIZATION, jwt)
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void retrievePostTest() throws Exception {
        given(postService.retrievePost(any()))
                .willReturn(postResponse());

        mockMvc.perform(get("/api/guestbook/posts/1")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value(TITLE))
                .andExpect(jsonPath("content").value(CONTENT));
    }

    @Test
    void retrieveNoPostFailTest() throws Exception {
        given(postService.retrievePost(any()))
                .willThrow(new PostNotFoundException(POST_ID));

        mockMvc.perform(get("/api/guestbook/posts/1"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void updatePostTest() throws Exception {
        String content = objectMapper.writeValueAsString(postUpdateRequest());

        given(postService.updatePost(any(), any()))
                .willReturn(postResponse());

        mockMvc.perform(put("/api/guestbook/posts")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value(TITLE))
                .andExpect(jsonPath("content").value(CONTENT));
    }

    @Test
    void noPost_updatePostFailTest() throws Exception {
        String content = objectMapper.writeValueAsString(postUpdateRequest());

        doThrow(new PostNotFoundException(POST_ID))
                .when(postService)
                .updatePost(any(), any());

        mockMvc.perform(put("/api/guestbook/posts")
                        .header(AUTHORIZATION, jwt)
                        .content(content)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void memberMisMatch_updatePostFailTest() throws Exception {
        PostUpdateRequest request = PostUpdateRequest.builder()
                .id(1L)
                .title("new title")
                .content("new content")
                .build();

        String content = objectMapper.writeValueAsString(request);

        doThrow(new PostWriterMismatchException(WRITER_EMAIL))
                .when(postService)
                .updatePost(any(), any());

        mockMvc.perform(put("/api/guestbook/posts")
                        .header(AUTHORIZATION, jwt)
                        .content(content)
                        .contentType(APPLICATION_JSON))
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

        mockMvc.perform(put("/api/guestbook/posts")
                        .header(AUTHORIZATION, jwt)
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deletePostTest() throws Exception {
        given(postService.deletePost(any(), any()))
                .willReturn(POST_ID);

        mockMvc.perform(delete("/api/guestbook/posts/1")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void noPost_deletePostFailTest() throws Exception {
        doThrow(new PostNotFoundException(2L))
                .when(postService)
                .deletePost(any(), any());

        mockMvc.perform(delete("/api/guestbook/posts/2")
                        .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void memberMisMatch_deletePostFailTest() throws Exception {
        doThrow(new PostWriterMismatchException(WRITER_EMAIL))
                .when(postService)
                .deletePost(any(), any());

        mockMvc.perform(delete("/api/guestbook/posts/1")
                        .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void queryPostsTest() throws Exception {
        PagedPostsResponse response = PagedPostsResponse.from(new PageImpl<Post>(
                Arrays.asList(PostFixture.post())));

        given(postService.queryPosts(any()))
                .willReturn(response);

        mockMvc.perform(get("/api/guestbook/posts/query/?page=1&size=10")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    private Key getKey() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> resolver = Class.forName("me.jun.common.security.JwtProvider");
        Field secret_key = resolver.getDeclaredField("SECRET_KEY");
        secret_key.setAccessible(true);
        return (Key) secret_key.get(Key.class);
    }
}