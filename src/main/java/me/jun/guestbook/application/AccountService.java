package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.AccountRequest;
import me.jun.guestbook.dto.AccountResponse;
import me.jun.guestbook.application.exception.DuplicatedEmailException;
import me.jun.guestbook.application.exception.EmailNotFoundException;
import me.jun.guestbook.domain.account.Account;
import me.jun.guestbook.domain.account.AccountRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponse login(AccountRequest dto) {
        Account account = dto.toEntity();
        account = accountRepository.findByEmail(account.getEmail())
                .orElseThrow(EmailNotFoundException::new);

        account.validate(dto.getPassword());

        return AccountResponse.from(account);
    }

    public AccountResponse register(AccountRequest dto) {
        Account account = dto.toEntity();

        try {
            account = accountRepository.save(account);

            return AccountResponse.from(account);
        }
        catch (DataIntegrityViolationException e) {
            throw new DuplicatedEmailException(e);
        }
    }

    public void deleteAccount(AccountRequest dto) {
        Account account = accountRepository.findByEmail(dto.getEmail())
                .orElseThrow(EmailNotFoundException::new);

        account.validate(dto.getPassword());

        accountRepository.deleteById(account.getId());
    }
}
