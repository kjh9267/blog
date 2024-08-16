package me.jun.display.application;

import me.jun.display.domain.repository.DisplayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.display.DisplayFixture.page;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DisplayServiceTest {

    @Mock
    private DisplayRepository displayRepository;

    private DisplayService displayService;

    @BeforeEach
    void setUp() {
        displayService = new DisplayService(displayRepository);
    }

    @Test
    void retrieveDisplayTest() {
        given(displayRepository.retrieveDisplay(anyInt(), anyInt()))
                .willReturn(page());


        assertThat(displayService.retrieveDisplay(0, 10).getPage().getSize())
                .isEqualTo(10);
    }
}