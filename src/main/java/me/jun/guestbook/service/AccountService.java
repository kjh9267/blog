package me.jun.guestbook.service;

import me.jun.guestbook.dao.AccountRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.dto.AccountInfoDto;
import me.jun.guestbook.dto.AccountLoginDto;
import me.jun.guestbook.dto.AccountRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

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

    public AccountInfoDto createAccount(AccountRegisterDto accountRegisterDto) {
        final Account account = Account.builder()
                .email(accountRegisterDto.getEmail())
                .name(accountRegisterDto.getName())
                .password(accountRegisterDto.getPassword())
                .build();

        try {
            Account newAccount = accountRepository.save(account);

            return AccountInfoDto.builder()
                    .email(newAccount.getEmail())
                    .name(newAccount.getName())
                    .posts(newAccount.getPosts())
                    .build();
        }
        catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("invalid email");
        }
    }
}
