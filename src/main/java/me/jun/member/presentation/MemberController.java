package me.jun.member.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.member.application.MemberService;
import me.jun.member.application.LoginService;
import me.jun.member.application.RegisterService;
import me.jun.member.application.dto.MemberRequest;
import me.jun.member.application.dto.TokenResponse;
import me.jun.member.presentation.hateoas.MemberEntityModelCreator;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final LoginService loginService;

    private final RegisterService registerService;

    private final MemberService memberService;

    private final MemberEntityModelCreator entityModelCreator;

    @PostMapping("/register")
    public ResponseEntity<RepresentationModel> register(@RequestBody @Valid MemberRequest requestDto) {
        registerService.register(requestDto);
        URI selfUri = createSelfUri();

        return ResponseEntity.created(selfUri)
                .body(entityModelCreator.createHyperlinks());
    }

    @PostMapping("/login")
    public ResponseEntity<EntityModel<TokenResponse>> login(@RequestBody @Valid MemberRequest requestDto) {
        TokenResponse tokenResponse = loginService.login(requestDto);
        URI selfUri = createSelfUri();

        return ResponseEntity.created(selfUri)
                .body(entityModelCreator.createEntityModel(tokenResponse));
    }

    @DeleteMapping("/leave")
    public ResponseEntity<RepresentationModel> leave(@MemberInfo me.jun.member.application.dto.MemberInfo memberInfo) {
        memberService.deleteMember(memberInfo.getId());
        return ResponseEntity.ok()
                .body(entityModelCreator.createHyperlinks());
    }

    private URI createSelfUri() {
        return linkTo(MemberController.class).slash("login")
                .withSelfRel()
                .toUri();
    }
}
