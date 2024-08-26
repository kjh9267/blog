package me.jun.core.member.application;

import me.jun.core.member.application.exception.DuplicatedEmailException;
import me.jun.core.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.core.member.MemberFixture.*;
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
    void registerTest() {
        given(memberRepository.save(any()))
                .willReturn(member());

        assertThat(registerService.register(memberRegisterRequest()))
                .isEqualToComparingFieldByField(memberResponse());
    }

    @Test
    void duplicatedEmail_registerFailTest() {
        given(memberRepository.save(any()))
                .willThrow(DuplicatedEmailException.class);

        assertThrows(DuplicatedEmailException.class,
                () -> assertThat(registerService.register(memberRegisterRequest()))
        );
    }
}