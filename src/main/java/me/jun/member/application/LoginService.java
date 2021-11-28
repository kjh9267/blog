package me.jun.member.application;

import lombok.RequiredArgsConstructor;
import me.jun.member.application.dto.MemberRequest;
import me.jun.member.application.dto.TokenResponse;
import me.jun.member.application.exception.EmailNotFoundException;
import me.jun.member.domain.Member;
import me.jun.member.domain.repository.MemberRepository;
import me.jun.common.security.JwtProvider;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final MemberRepository memberRepository;

    private final JwtProvider jwtProvider;

    @Async
    @Cacheable(cacheNames = "tokenStore")
    @Transactional(readOnly = true)
    public CompletableFuture<TokenResponse> login(MemberRequest request) {
        String email = request.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(EmailNotFoundException::new);

        member.validate(request.getPassword());
        String jwt = jwtProvider.createJwt(email);

        return CompletableFuture.completedFuture(
                TokenResponse.from(jwt)
        );
    }
}
