package me.jun.guestbook.application;

import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.AccountRepository;
import me.jun.guestbook.dto.AccountRequestDto;
import me.jun.guestbook.dto.AccountResponseDto;
import me.jun.guestbook.exception.DuplicatedEmailException;
import me.jun.guestbook.exception.EmailNotFoundException;
import me.jun.guestbook.exception.WrongPasswordException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    private Account account;

    String password = "pass";

    @Before
    public void setUp() {
        account = Account.builder()
                .name("jun")
                .email("testuser@email.com")
                .password(password)
                .build();
    }

    @Test
    public void readAccountTest() {
        // Given
        accountRepository.save(account);

        final AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .email("testuser@email.com")
                .password(password)
                .build();

        // When
        final AccountResponseDto accountResponseDto = accountService.login(accountRequestDto);

        // Then
        assertThat(accountResponseDto).isEqualToComparingOnlyGivenFields(accountRequestDto, "email");
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getAccountFailTest() {
        // Expected
        expectedException.expect(WrongPasswordException.class);
        expectedException.expectMessage("wrong password");

        // Given
        accountRepository.save(account);

        final AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .email("testuser@email.com")
                .password("abc")
                .build();

        // When
        accountService.login(accountRequestDto);
    }

    @Test
    public void createAccountTest() {
        // Given
        accountRepository.save(account);

        final AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .name(account.getName())
                .password(password)
                .email("new_user@email.com")
                .build();

        // When
        final AccountResponseDto accountResponseDto = accountService.createAccount(accountRequestDto);

        // Then
        assertThat(accountResponseDto.getEmail()).isEqualTo(accountRequestDto.getEmail());
        assertThat(accountResponseDto.getName()).isEqualTo(accountRequestDto.getName());
    }

    @Test
    public void createAccountFailTest() {
        // Expected
        expectedException.expect(DuplicatedEmailException.class);
        expectedException.expectMessage("Email already exists");

        // Given
        accountRepository.save(account);

        final AccountRequestDto accountRegisterDto = AccountRequestDto.builder()
                .name(account.getName())
                .password("pass")
                .email(account.getEmail())
                .build();

        // When
        accountService.createAccount(accountRegisterDto);
    }

    @Test
    public void deleteAccountTest() {
        // Expected
        expectedException.expect(EmailNotFoundException.class);

        // Given
        final AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .email(account.getEmail())
                .password(password)
                .build();

        // When
        accountService.deleteAccount(accountRequestDto);
        accountService.login(accountRequestDto);
    }
}
