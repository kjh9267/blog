package me.jun.guestbook.controller;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.AccountRequestDto;
import me.jun.guestbook.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public String register(@RequestBody AccountRequestDto requestDto) {
        accountService.createAccount(requestDto);

        return "redirect:/index";
    }
}
