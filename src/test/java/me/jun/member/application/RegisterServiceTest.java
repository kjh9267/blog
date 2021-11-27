package me.jun.member.application;

import me.jun.member.application.exception.DuplicatedEmailException;
import me.jun.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutionException;

import static me.jun.member.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    private RegisterService registerService;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        registerService = new RegisterService(memberRepository);
    }

    @Test
    void registerTest() throws ExecutionException, InterruptedException {
        given(memberRepository.save(any()))
                .willReturn(member());

        assertThat(registerService.register(memberRequest()).get())
                .isEqualToComparingFieldByField(memberResponse());
    }

    @Test
    void duplicatedEmail_registerFailTest() {
        given(memberRepository.save(any()))
                .willThrow(DuplicatedEmailException.class);

        assertThrows(DuplicatedEmailException.class,
                () -> assertThat(registerService.register(memberRequest()))
        );
    }
}