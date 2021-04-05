package me.jun.guestbook.common;

import me.jun.guestbook.application.AccountService;
import me.jun.guestbook.dto.AccountRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        accountService.createAccount(
                AccountRequestDto.builder()
                        .email("user@email.com")
                        .password("pass")
                        .name("jun")
                        .build()
        );
    }
}
