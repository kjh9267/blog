package me.jun.core.member.application;

import lombok.RequiredArgsConstructor;
import me.jun.core.member.application.dto.MemberResponse;
import me.jun.core.member.application.dto.RegisterRequest;
import me.jun.core.member.application.exception.DuplicatedEmailException;
import me.jun.core.member.domain.Member;
import me.jun.core.member.domain.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {

    private final MemberRepository memberRepository;

    public MemberResponse register(RegisterRequest request) {
        Member member = request.toEntity();
        String email = member.getEmail();

        try {
            member = memberRepository.save(member);
            return MemberResponse.from(member);
        }
        catch (DataIntegrityViolationException e) {
            throw new DuplicatedEmailException(email);
        }
    }
}
