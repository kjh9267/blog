package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.AccountRepository;
import me.jun.guestbook.dto.AccountRequestDto;
import me.jun.guestbook.dto.AccountResponseDto;
import me.jun.guestbook.exception.DuplicatedEmailException;
import me.jun.guestbook.exception.EmailNotFoundException;
import me.jun.guestbook.exception.WrongPasswordException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponseDto login(AccountRequestDto accountRequestDto) {
        Account requestAccount = accountRequestDto.toEntity();
        String requestEmail = requestAccount.getEmail();
        String requestPassword = requestAccount.getPassword();

        Account account = accountRepository.findByEmail(requestEmail)
                .orElseThrow(EmailNotFoundException::new);

        if (account.isCorrect(requestPassword)) {
            throw new WrongPasswordException();
        }

        return AccountResponseDto.from(account);
    }

    public AccountResponseDto createAccount(AccountRequestDto accountRequestDto) {
        Account account = Account.builder()
                .email(accountRequestDto.getEmail())
                .name(accountRequestDto.getName())
                .password(accountRequestDto.getPassword())
                .build();

        try {
            Account newAccount = accountRepository.save(account);

            return AccountResponseDto.from(newAccount);
        }
        catch (DataIntegrityViolationException e) {
            throw new DuplicatedEmailException();
        }
    }

    public void deleteAccount(AccountRequestDto accountRequestDto) {
        Account requestAccount = accountRequestDto.toEntity();
        String requestEmail = requestAccount.getEmail();
        String requestPassword = requestAccount.getPassword();

        Account account = accountRepository.findByEmail(requestEmail)
                .orElseThrow(IllegalArgumentException::new);

        if (account.isCorrect(requestPassword)) {
            throw new WrongPasswordException();
        }

        accountRepository.deleteById(account.getId());
    }
}
