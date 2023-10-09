package me.jun.member.application;

import me.jun.common.event.EventPublisher;
import me.jun.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.jun.common.event.EventFixture.memberLeaveEvent;
import static me.jun.member.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, eventPublisher);
    }

    @Test
    void retrieveMemberByEmailTest() {
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(member()));

        assertThat(memberService.retrieveMemberBy("testuser@email.com"))
                .isEqualToComparingFieldByField(memberResponse());
    }

    @Test
    void deleteMemberTest() {
        memberService.deleteMember(EMAIL);

        verify(eventPublisher).raise(memberLeaveEvent());
    }
}