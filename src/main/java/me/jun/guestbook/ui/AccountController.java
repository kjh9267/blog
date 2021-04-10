package me.jun.guestbook.ui;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.AccountService;
import me.jun.guestbook.dto.AccountRequest;
import me.jun.guestbook.dto.AccountResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public String register(@RequestBody AccountRequest requestDto) {
        accountService.register(requestDto);

        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(@RequestBody AccountRequest requestDto,
                        HttpSession httpSession) {
        final AccountResponse accountInfo = accountService.login(requestDto);

        httpSession.setAttribute("login", accountInfo);
        httpSession.setMaxInactiveInterval(60 * 10);

        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
