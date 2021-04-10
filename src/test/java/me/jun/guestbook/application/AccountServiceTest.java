package me.jun.guestbook.application;

import me.jun.guestbook.application.exception.DuplicatedEmailException;
import me.jun.guestbook.application.exception.EmailNotFoundException;
import me.jun.guestbook.domain.account.Account;
import me.jun.guestbook.domain.account.AccountRepository;
import me.jun.guestbook.dto.AccountRequest;
import me.jun.guestbook.dto.AccountResponse;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    private Account account;

    private AccountRequest accountRequest;

    private final String name = "testuser";

    private final String email = "testuser@email.com";

    private final String password = "pass";

    @BeforeEach
    public void setUp() {
        accountService = new AccountService(accountRepository);

        account = Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        accountRequest = AccountRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    @Test
    void loginTest() {
        given(accountRepository.findByEmail(email)).willReturn(Optional.of(account));

        // When
        AccountResponse accountResponse = accountService.login(accountRequest);

        // Then
        assertAll(
                () -> assertThat(accountResponse).isInstanceOf(AccountResponse.class),
                () -> assertThat(accountResponse.getName()).isEqualTo(name),
                () -> assertThat(accountResponse.getEmail()).isEqualTo(email)
        );

    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    void NoAccount_loginFailTest() {
        given(accountRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class,
                () -> accountService.login(accountRequest)
        );
    }

    @Test
    void registerTest() {
        given(accountRepository.save(account)).willReturn(account);

        AccountResponse accountResponse = accountService.register(accountRequest);

        assertAll(
                () -> assertThat(accountResponse).isInstanceOf(AccountResponse.class),
                () -> assertThat(accountResponse.getName()).isEqualTo(name),
                () -> assertThat(accountResponse.getEmail()).isEqualTo(email)
        );
    }

    @Test
    void registerFailTest() {
        given(accountRepository.save(account)).willThrow(new DataIntegrityViolationException(""));

        assertThrows(DuplicatedEmailException.class,
                () -> accountService.register(accountRequest)
        );
    }
}
