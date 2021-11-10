package me.jun.guest.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guest.application.GuestService;
import me.jun.guest.application.LoginService;
import me.jun.guest.application.RegisterService;
import me.jun.guest.application.dto.GuestInfo;
import me.jun.guest.application.dto.GuestRequest;
import me.jun.guest.application.dto.TokenResponse;
import me.jun.guest.presentation.hateoas.GuestEntityModelCreator;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GuestController {

    private final LoginService loginService;

    private final RegisterService registerService;

    private final GuestService guestService;

    private final GuestEntityModelCreator entityModelCreator;

    @PostMapping("/register")
    public ResponseEntity<RepresentationModel> register(@RequestBody GuestRequest requestDto) {
        registerService.register(requestDto);
        URI selfUri = createSelfUri();

        return ResponseEntity.created(selfUri)
                .body(entityModelCreator.createHyperlinks());
    }

    @PostMapping("/login")
    public ResponseEntity<EntityModel<TokenResponse>> login(@RequestBody GuestRequest requestDto) {
        TokenResponse tokenResponse = loginService.login(requestDto);
        URI selfUri = createSelfUri();

        return ResponseEntity.created(selfUri)
                .body(entityModelCreator.createEntityModel(tokenResponse));
    }

    @DeleteMapping("/leave")
    public ResponseEntity<RepresentationModel> leave(@Guest GuestInfo guestInfo) {
        guestService.deleteGuest(guestInfo.getId());
        return ResponseEntity.ok()
                .body(entityModelCreator.createHyperlinks());
    }

    private URI createSelfUri() {
        return linkTo(GuestController.class).slash("login")
                .withSelfRel()
                .toUri();
    }
}
