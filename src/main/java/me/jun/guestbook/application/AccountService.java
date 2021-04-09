package me.jun.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.AccountRepository;
import me.jun.guestbook.dto.AccountRequestDto;
import me.jun.guestbook.dto.AccountResponseDto;
import me.jun.guestbook.exception.DuplicatedEmailException;
import me.jun.guestbook.exception.EmailNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponseDto login(AccountRequestDto dto) {
        Account account = dto.toEntity();
        account = accountRepository.findByEmail(account.getEmail())
                .orElseThrow(EmailNotFoundException::new);

        account.validate(dto.getPassword());

        return AccountResponseDto.from(account);
    }

    public AccountResponseDto createAccount(AccountRequestDto dto) {
        Account account = dto.toEntity();

        try {
            account = accountRepository.save(account);

            return AccountResponseDto.from(account);
        }
        catch (DataIntegrityViolationException e) {
            throw new DuplicatedEmailException(e);
        }
    }

    public void deleteAccount(AccountRequestDto dto) {
        Account account = accountRepository.findByEmail(dto.getEmail())
                .orElseThrow(EmailNotFoundException::new);

        account.validate(dto.getPassword());

        accountRepository.deleteById(account.getId());
    }
}
