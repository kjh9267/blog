package me.jun.guestbook.controller;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.AccountRequestDto;
import me.jun.guestbook.dto.AccountResponseDto;
import me.jun.guestbook.service.AccountService;
import org.springframework.http.MediaType;
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
    public String register(@RequestBody AccountRequestDto requestDto) {
        accountService.createAccount(requestDto);

        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(@RequestBody AccountRequestDto requestDto,
                        HttpSession httpSession) {
        final AccountResponseDto accountInfo = accountService.login(requestDto);

        httpSession.setAttribute("login", accountInfo);
        httpSession.setMaxInactiveInterval(60 * 10);

        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
