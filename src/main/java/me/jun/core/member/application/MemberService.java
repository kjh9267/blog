package me.jun.core.member.application;

import lombok.RequiredArgsConstructor;
import me.jun.common.event.EventPublisher;
import me.jun.common.event.MemberLeaveEvent;
import me.jun.core.member.application.dto.MemberResponse;
import me.jun.core.member.application.exception.MemberNotFoundException;
import me.jun.core.member.domain.Member;
import me.jun.core.member.domain.repository.MemberRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final EventPublisher eventPublisher;

    @Cacheable(cacheNames = "memberStore", key = "#email")
    @Transactional(readOnly = true)
    public MemberResponse retrieveMemberBy(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));

        return MemberResponse.from(member);
    }

    public void deleteMember(String email) {
        memberRepository.deleteByEmail(email);
        eventPublisher.raise(new MemberLeaveEvent(email));
    }
}
