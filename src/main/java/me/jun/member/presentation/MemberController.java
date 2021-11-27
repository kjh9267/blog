package me.jun.member.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.member.application.LoginService;
import me.jun.member.application.MemberService;
import me.jun.member.application.RegisterService;
import me.jun.member.application.dto.MemberInfo;
import me.jun.member.application.dto.MemberRequest;
import me.jun.member.application.dto.MemberResponse;
import me.jun.member.application.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final LoginService loginService;

    private final RegisterService registerService;

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Mono<MemberResponse>> register(@RequestBody @Valid MemberRequest request) {
        Mono<MemberResponse> memberResponseMono = Mono.fromCompletionStage(
                registerService.register(request)
        ).log();

        return ResponseEntity.ok()
                .body(memberResponseMono);
    }

    @PostMapping("/login")
    public ResponseEntity<Mono<TokenResponse>> login(@RequestBody @Valid MemberRequest request) {
        Mono<TokenResponse> tokenResponseMono = Mono.fromCompletionStage(
                loginService.login(request)
        ).log();

        return ResponseEntity.ok()
                .body(tokenResponseMono);
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Mono<Long>> leave(@Member MemberInfo memberInfo) {
        Mono<Long> longMono = Mono.fromCompletionStage(
                memberService.deleteMember(memberInfo.getId())
        ).log();

        return ResponseEntity.ok()
                .body(longMono);
    }
}
