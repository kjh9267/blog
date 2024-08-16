package me.jun.display.presentation;

import me.jun.display.application.DisplayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static me.jun.display.DisplayFixture.categoryArticlesResponse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class DisplayControllerTest {

    @MockBean
    private DisplayService displayService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        given(displayService.retrieveDisplay(anyInt(), anyInt()))
                .willReturn(categoryArticlesResponse());
    }

    @Test
    void displayTest() throws Exception {
        mockMvc.perform(
                get("/api/display?page=0&size=10")
                .accept(APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(jsonPath("page").exists());
    }
}