package me.jun.guestbook.service;

import me.jun.guestbook.dao.AccountRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.dto.AccountResponseDto;
import me.jun.guestbook.dto.AccountRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountResponseDto readAccount(AccountRequestDto accountRequestDto) {
        final Account requestAccount = accountRequestDto.toEntity();
        final String requestEmail = requestAccount.getEmail();

        final Account account = accountRepository.findByEmail(requestEmail)
                .orElseThrow(IllegalArgumentException::new);

        if (!requestAccount.getPassword().equals(account.getPassword())) {
            throw new IllegalArgumentException("wrong password");
        }

        return AccountResponseDto.from(account);
    }

    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {
        final Account account = Account.builder()
                .email(accountRequestDto.getEmail())
                .name(accountRequestDto.getName())
                .password(accountRequestDto.getPassword())
                .build();

        try {
            Account newAccount = accountRepository.save(account);

            return AccountResponseDto.from(newAccount);
        }
        catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("invalid email");
        }
    }

    public void deleteAccount(AccountRequestDto accountRequestDto) {
        final Account requestAccount = accountRequestDto.toEntity();
        final String requestEmail = requestAccount.getEmail();

        final Account account = accountRepository.findByEmail(requestEmail)
                .orElseThrow(IllegalArgumentException::new);

        if (!requestAccount.getPassword().equals(account.getPassword())) {
            throw new IllegalArgumentException("wrong password");
        }

        accountRepository.deleteById(account.getId());
    }
}
