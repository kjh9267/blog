package me.jun.guestbook.guest.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.dto.GuestRequest;
import me.jun.guestbook.guest.application.GuestService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@Disabled
public class GuestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GuestService guestService;

    MockHttpSession mockHttpSession;

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
        final GuestRequest requestDto = GuestRequest.builder()
                .name("jun")
                .email("testuser@email.com")
                .password("pass")
                .build();

        guestService.register(requestDto);

        final String content = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/login")
                    .content(content)
                    .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginSessionTest() throws Exception {
        final GuestRequest requestDto = GuestRequest.builder()
                .name("jun")
                .email("testuser@email.com")
                .password("pass")
                .build();

        guestService.register(requestDto);

        mockHttpSession = new MockHttpSession();

        mockHttpSession.setAttribute("login", requestDto);

        mockMvc.perform(get("/index")
                .session(mockHttpSession)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}