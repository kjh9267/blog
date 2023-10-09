package me.jun.member.application;

import lombok.RequiredArgsConstructor;
import me.jun.member.application.dto.MemberRequest;
import me.jun.member.application.dto.MemberResponse;
import me.jun.member.application.exception.DuplicatedEmailException;
import me.jun.member.domain.Member;
import me.jun.member.domain.repository.MemberRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterService {

    private final MemberRepository memberRepository;

    public MemberResponse register(MemberRequest request) {
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
