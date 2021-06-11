package me.jun.guestbook.common;

import me.jun.guestbook.guest.application.GuestAuthService;
import me.jun.guestbook.dto.GuestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {

    @Autowired
    GuestAuthService guestAuthService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        guestAuthService.register(
                GuestRequest.builder()
                        .email("user@email.com")
                        .password("pass")
                        .name("jun")
                        .build()
        );
    }
}
