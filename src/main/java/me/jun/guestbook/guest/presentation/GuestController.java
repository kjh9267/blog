package me.jun.guestbook.guest.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.application.LoginService;
import me.jun.guestbook.guest.application.RegisterService;
import me.jun.guestbook.guest.presentation.dto.GuestRequest;
import me.jun.guestbook.guest.presentation.dto.TokenResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GuestController {

    private final LoginService loginService;

    private final RegisterService registerService;

    private final GuestEntityModelCreator entityModelCreator;

    @PostMapping("/register")
    public ResponseEntity<RepresentationModel> register(@RequestBody GuestRequest requestDto) {
        registerService.register(requestDto);
        URI selfUri = createSelfUri();

        return ResponseEntity.created(selfUri)
                .body(entityModelCreator.createRegisterRepresentationModel());
    }

    @PostMapping("/login")
    public ResponseEntity<EntityModel<TokenResponse>> login(@RequestBody GuestRequest requestDto) {
        TokenResponse tokenResponse = loginService.login(requestDto);
        URI selfUri = createSelfUri();

        return ResponseEntity.created(selfUri)
                .body(entityModelCreator.createLoginEntityModel(tokenResponse));
    }

    private URI createSelfUri() {
        return linkTo(GuestController.class).slash("login")
                .withSelfRel()
                .toUri();
    }
}
