package me.jun.member.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.member.application.LoginService;
import me.jun.member.application.MemberService;
import me.jun.member.application.RegisterService;
import me.jun.member.application.dto.MemberInfo;
import me.jun.member.application.dto.MemberRequest;
import me.jun.member.application.dto.MemberResponse;
import me.jun.member.application.dto.TokenResponse;
import me.jun.support.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final LoginService loginService;

    private final RegisterService registerService;

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberResponse> register(@RequestBody @Valid MemberRequest request) {
        MemberResponse response = registerService.register(request);

        return ResponseEntity.ok()
                .body(response);
    }

//    @Cacheable(cacheNames = "tokenStore", key = "#request.email")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid MemberRequest request) {

        TokenResponse response = loginService.login(request);

        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Void> leave(@Member MemberInfo memberInfo) {
        memberService.deleteMember(memberInfo.getEmail());

        return ResponseEntity.ok()
                .build();
    }
}
