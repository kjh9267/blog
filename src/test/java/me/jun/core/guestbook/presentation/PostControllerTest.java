package me.jun.core.guestbook.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.common.security.JwtProvider;
import me.jun.core.guestbook.application.PostService;
import me.jun.core.guestbook.application.dto.PagedPostsResponse;
import me.jun.core.guestbook.application.dto.PostCreateRequest;
import me.jun.core.guestbook.application.dto.PostUpdateRequest;
import me.jun.core.guestbook.application.exception.PostNotFoundException;
import me.jun.core.guestbook.domain.exception.PostWriterMismatchException;
import me.jun.core.member.application.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static me.jun.core.guestbook.PostFixture.*;
import static me.jun.core.member.MemberFixture.memberResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

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
    void setUp() {
        jwt = jwtProvider.createJwt(POST_WRITER_EMAIL);

        given(memberService.retrieveMemberBy(any()))
                .willReturn(memberResponse());
    }

    @Test
    void createPostTest() throws Exception {
        String content = objectMapper.writeValueAsString(postCreateRequest());

        given(postService.createPost(any()))
                .willReturn(postResponse());

        ResultActions resultActions = mockMvc.perform(post("/api/guestbook/posts")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, jwt))
                .andDo(print());

        expectedJson(resultActions);
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
    void retrievePostTest() throws Exception {
        given(postService.retrievePost(any()))
                .willReturn(postResponse());

        ResultActions resultActions = mockMvc.perform(get("/api/guestbook/posts/1")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print());

        expectedJson(resultActions);
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
    void updatePostTest() throws Exception {
        String content = objectMapper.writeValueAsString(postUpdateRequest());

        given(postService.updatePost(any()))
                .willReturn(postResponse());

        mockMvc.perform(put("/api/guestbook/posts")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links").exists())
                .andExpect(jsonPath("id").value(POST_ID))
                .andExpect(jsonPath("title").value(TITLE))
                .andExpect(jsonPath("content").value(CONTENT));
    }

    @Test
    void noPost_updatePostFailTest() throws Exception {
        String content = objectMapper.writeValueAsString(postUpdateRequest());

        doThrow(new PostNotFoundException(POST_ID))
                .when(postService)
                .updatePost(any());

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

        doThrow(new PostWriterMismatchException(POST_WRITER_EMAIL))
                .when(postService)
                .updatePost(any());

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
    void deletePostTest() throws Exception {
        given(postService.deletePost(any()))
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
                .deletePost(any());

        mockMvc.perform(delete("/api/guestbook/posts/2")
                        .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void memberMisMatch_deletePostFailTest() throws Exception {
        doThrow(new PostWriterMismatchException(POST_WRITER_EMAIL))
                .when(postService)
                .deletePost(any());

        mockMvc.perform(delete("/api/guestbook/posts/1")
                        .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void queryPostsTest() throws Exception {
        PagedPostsResponse response = pagedPostsResponse();
        String expected = objectMapper.writeValueAsString(response);

        given(postService.queryPosts(any()))
                .willReturn(response);

        mockMvc.perform(get("/api/guestbook/posts/query/?page=1&size=10")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));
    }
}