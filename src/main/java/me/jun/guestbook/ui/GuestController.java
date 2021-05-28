package me.jun.guestbook.ui;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.GuestService;
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

    private final GuestService guestService;

    @PostMapping("/register")
    public String register(@RequestBody GuestRequest requestDto) {
        guestService.register(requestDto);

        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(@RequestBody GuestRequest requestDto,
                        HttpSession httpSession) {
        final GuestResponse accountInfo = guestService.login(requestDto);

        httpSession.setAttribute("login", accountInfo);
        httpSession.setMaxInactiveInterval(60 * 10);

        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
