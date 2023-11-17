package me.jun.member.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.common.hateoas.LinkCreator;
import me.jun.member.application.LoginService;
import me.jun.member.application.MemberService;
import me.jun.member.application.RegisterService;
import me.jun.member.application.dto.MemberInfo;
import me.jun.member.application.dto.MemberRequest;
import me.jun.member.application.dto.MemberResponse;
import me.jun.member.application.dto.TokenResponse;
import me.jun.common.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final LoginService loginService;

    private final RegisterService registerService;

    private final MemberService memberService;

    private final LinkCreator linkCreator;

    @PostMapping(
            value = "/register",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MemberResponse> register(@RequestBody @Valid MemberRequest request) {
        MemberResponse response = registerService.register(request);

        linkCreator.createLink(getClass(), response);

        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping(
            value = "/login",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid MemberRequest request) {

        TokenResponse response = loginService.login(request);

        linkCreator.createLink(getClass(), response);

        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping(
            value = "/leave",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> leave(@Member MemberInfo memberInfo) {
        memberService.deleteMember(memberInfo.getEmail());

        return ResponseEntity.noContent()
                .build();
    }
}
