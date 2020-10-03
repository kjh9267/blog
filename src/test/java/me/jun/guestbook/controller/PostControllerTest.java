package me.jun.guestbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.dao.AccountRepository;
import me.jun.guestbook.dao.PostRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.dto.PostCreateRequestDto;
import me.jun.guestbook.dto.PostReadRequestDto;
import me.jun.guestbook.service.PostService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Test
    public void createPostTest() throws Exception {
        accountRepository.save(Account.builder()
                .email("testuser@email.com")
                .name("jun")
                .password("pass")
                .build());

        final PostCreateRequestDto requestDto = PostCreateRequestDto.builder()
                .title("my title")
                .content("my content")
                .accountEmail("testuser@email.com")
                .build();

        final String content = objectMapper.writeValueAsString(requestDto);

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
        final Account account = accountRepository.save(Account.builder()
                .email("testuser@email.com")
                .name("jun")
                .password("pass")
                .build());

        postService.createPost(PostCreateRequestDto.builder()
                .accountEmail(account.getEmail())
                .title("my title")
                .content("my content")
                .build());

        mockMvc.perform(get("/post/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
