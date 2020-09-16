package me.jun.guestbook.service;

import me.jun.guestbook.dao.AccountRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.dto.AccountInfoDto;
import me.jun.guestbook.dto.AccountRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountInfoDto getAccount(AccountRequestDto accountRequestDto) {
        final Account account = getAccountFromRepository(accountRequestDto);

        return AccountInfoDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .posts(account.getPosts())
                .build();
    }

    private Account getAccountFromRepository(AccountRequestDto accountRequestDto) {
        final String requestEmail = accountRequestDto.getEmail();

        final Account account = accountRepository.findByEmail(requestEmail)
                .orElseThrow(IllegalArgumentException::new);

        if (!accountRequestDto.getPassword().equals(account.getPassword())) {
            throw new IllegalArgumentException("wrong password");
        }
        return account;
    }

    public AccountInfoDto createAccount(AccountRequestDto accountRequestDto) {
        final Account account = Account.builder()
                .email(accountRequestDto.getEmail())
                .name(accountRequestDto.getName())
                .password(accountRequestDto.getPassword())
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

    public void deleteAccount(AccountRequestDto accountRequestDto) {
        final Account account = getAccountFromRepository(accountRequestDto);

        accountRepository.deleteById(account.getId());
    }
}
