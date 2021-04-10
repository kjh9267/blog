package me.jun.guestbook.ui;

import me.jun.guestbook.dto.AccountRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IndexControllerTest {

    @Autowired
    MockMvc mockMvc;

    MockHttpSession mockHttpSession;

    @Test
    public void indexTest() throws Exception {
        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .name("testuser")
                .password("pass")
                .email("testuser@email.com")
                .build();

        mockHttpSession = new MockHttpSession();

        mockHttpSession.setAttribute("login", accountRequestDto);

        mockMvc.perform(get("/index")
                    .session(mockHttpSession))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}
