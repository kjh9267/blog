package me.jun.guestbook.guest.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.guest.presentation.dto.GuestRequest;
import me.jun.guestbook.guest.presentation.dto.TokenResponse;
import me.jun.guestbook.guest.application.GuestAuthService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GuestAuthService guestAuthService;

    @Test
    public void registerTest() throws Exception {
        final GuestRequest requestDto = GuestRequest.builder()
                .name("jun")
                .email("testuser@email.com")
                .password("pass")
                .build();

        final String content = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/register")
                    .contentType("application/json")
                    .content(content))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginTest() throws Exception {
        GuestRequest request = GuestRequest.builder()
                .name("jun")
                .email("testuser@email.com")
                .password("pass")
                .build();

        given(guestAuthService.login(any()))
                .willReturn(TokenResponse.from("1.2.3"));

        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/login")
                    .content(content)
                    .contentType("application/json")
                    .accept("application/hal+json"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/login"))
                .andExpect(jsonPath("access_token").value("1.2.3"))
                .andExpect(jsonPath("_links.self.href").value("http://localhost/login"));
    }

    @Test
    public void loginSessionTest() throws Exception {
        final GuestRequest requestDto = GuestRequest.builder()
                .name("jun")
                .email("testuser@email.com")
                .password("pass")
                .build();

        guestAuthService.register(requestDto);

        mockMvc.perform(get("/index")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}