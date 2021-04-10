package me.jun.guestbook.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.application.PostService;
import me.jun.guestbook.domain.account.Account;
import me.jun.guestbook.domain.account.AccountRepository;
import me.jun.guestbook.dto.AccountRequest;
import me.jun.guestbook.dto.PostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
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

    AccountRequest requestDto;

    MockHttpSession mockHttpSession;

    @BeforeEach
    public void setUp() {
        requestDto = AccountRequest.builder()
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

        PostRequest requestDto = PostRequest.builder()
                .title("my title")
                .content("my content")
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

    @Disabled
    @Test
    public void readPostTest() throws Exception {
        Account account = accountRepository.save(Account.builder()
                .email("testuser@email.com")
                .name("jun")
                .password("pass")
                .build());

        postService.createPost(PostRequest.builder()
                .title("my title")
                .content("my content")
                .build(), 1L);

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

        postService.createPost(PostRequest.builder()
                .title("my title")
                .content("my content")
                .build(), 1L);

        mockMvc.perform(delete("/post/1")
                    .session(mockHttpSession))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Disabled
    @Test
    public void updatePostTest() throws Exception {
        Account account = accountRepository.save(Account.builder()
                .email("testuser@email.com")
                .name("jun")
                .password("pass")
                .build());

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
                    .session(mockHttpSession)
                    .content(content)
                    .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
