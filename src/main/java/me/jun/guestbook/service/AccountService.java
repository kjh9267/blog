package me.jun.guestbook.service;

import me.jun.guestbook.dao.AccountRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.dto.AccountInfoDto;
import me.jun.guestbook.dto.AccountLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountInfoDto getAccount(AccountLoginDto accountLoginDto) {
        final String requestEmail = accountLoginDto.getEmail();

        final Account account = accountRepository.findByEmail(requestEmail)
                .orElseThrow(IllegalArgumentException::new);

        if (!accountLoginDto.getPassword().equals(account.getPassword())) {
            throw new IllegalArgumentException("wrong password");
        }

        return AccountInfoDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .build();
    }
}
