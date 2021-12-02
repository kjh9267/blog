package me.jun.member.application;

import lombok.RequiredArgsConstructor;
import me.jun.member.application.exception.DuplicatedEmailException;
import me.jun.member.domain.Member;
import me.jun.member.domain.repository.MemberRepository;
import me.jun.member.application.dto.MemberRequest;
import me.jun.member.application.dto.MemberResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {

    private final MemberRepository memberRepository;

    public MemberResponse register(MemberRequest request) {
        Member member = request.toEntity();

        try {
            member = memberRepository.save(member);
            return MemberResponse.from(member);
        }
        catch (DataIntegrityViolationException e) {
            throw new DuplicatedEmailException(e);
        }
    }
}
