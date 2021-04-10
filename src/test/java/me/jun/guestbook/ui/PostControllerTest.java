package me.jun.guestbook.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.application.PostService;
import me.jun.guestbook.dto.AccountRequestDto;
import me.jun.guestbook.dto.PostCreateRequestDto;
import me.jun.guestbook.dto.PostUpdateRequestDto;
import me.jun.guestbook.domain.account.Account;
import me.jun.guestbook.domain.account.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    PostService postService;

    AccountRequestDto requestDto;

    MockHttpSession mockHttpSession;

    @Before
    public void setUp() {
        requestDto = AccountRequestDto.builder()
                .name("jun")
                .email("testuser@email.com")
                .password("pass")
                .build();

        mockHttpSession = new MockHttpSession();

        mockHttpSession.setAttribute("login", requestDto);
    }

    @Test
    public void createPostTest() throws Exception {
        accountRepository.save(Account.builder()
                .email("testuser@email.com")
                .name("jun")
                .password("pass")
                .build());

        PostCreateRequestDto requestDto = PostCreateRequestDto.builder()
                .title("my title")
                .content("my content")
                .accountEmail("testuser@email.com")
                .build();

        String content = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/post")
                    .session(mockHttpSession)
                    .content(content)
                    .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/index")
                    .session(mockHttpSession))
                .andDo(print());
    }

    @Test
    public void readPostTest() throws Exception {
        Account account = accountRepository.save(Account.builder()
                .email("testuser@email.com")
                .name("jun")
                .password("pass")
                .build());

        postService.createPost(PostCreateRequestDto.builder()
                .accountEmail(account.getEmail())
                .title("my title")
                .content("my content")
                .build());

        mockMvc.perform(get("/post/1")
                    .session(mockHttpSession))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deletePostTest() throws Exception {
        Account account = accountRepository.save(Account.builder()
                .email("testuser@email.com")
                .name("jun")
                .password("pass")
                .build());

        postService.createPost(PostCreateRequestDto.builder()
                .accountEmail(account.getEmail())
                .title("my title")
                .content("my content")
                .build());

        mockMvc.perform(delete("/post/1")
                    .session(mockHttpSession))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void updatePostTest() throws Exception {
        Account account = accountRepository.save(Account.builder()
                .email("testuser@email.com")
                .name("jun")
                .password("pass")
                .build());

        postService.createPost(PostCreateRequestDto.builder()
                .accountEmail(account.getEmail())
                .title("my title")
                .content("my content")
                .build());

        String content = objectMapper.writeValueAsString(PostUpdateRequestDto.builder()
                .accountEmail("testuser@email.com")
                .title("new title")
                .content("new content")
                .password("pass")
                .build());

        mockMvc.perform(put("/post/1")
                    .session(mockHttpSession)
                    .content(content)
                    .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
