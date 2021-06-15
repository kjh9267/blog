package me.jun.guestbook.guest.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.GuestRequest;
import me.jun.guestbook.dto.TokenResponse;
import me.jun.guestbook.guest.application.GuestAuthService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
public class GuestController {

    private final GuestAuthService guestAuthService;

    @PostMapping("/register")
    public String register(@RequestBody GuestRequest requestDto) {
        guestAuthService.register(requestDto);

        return "redirect:/index";
    }

    @PostMapping("/login")
    public ResponseEntity<EntityModel<TokenResponse>> login(@RequestBody GuestRequest requestDto) {
        TokenResponse tokenResponse = guestAuthService.login(requestDto);
        Link selfLink = linkTo(GuestController.class).slash("login")
                .withSelfRel();
        URI selfUri = selfLink.toUri();

        return ResponseEntity.created(selfUri)
                .body(
                        EntityModel.of(tokenResponse)
                                .add(selfLink)
                );
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
