package me.jun.guestbook.guest.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.application.GuestAuthService;
import me.jun.guestbook.dto.GuestRequest;
import me.jun.guestbook.dto.GuestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

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
    public String login(@RequestBody GuestRequest requestDto,
                        HttpSession httpSession) {
        final GuestResponse accountInfo = guestAuthService.login(requestDto);

        httpSession.setAttribute("login", accountInfo);
        httpSession.setMaxInactiveInterval(60 * 10);

        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
