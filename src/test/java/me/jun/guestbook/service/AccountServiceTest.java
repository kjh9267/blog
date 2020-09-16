package me.jun.guestbook.service;

import me.jun.guestbook.dao.AccountRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.dto.AccountInfoDto;
import me.jun.guestbook.dto.AccountLoginDto;
import me.jun.guestbook.dto.AccountRegisterDto;
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

    @Before
    public void setUp() {
        account = Account.builder()
                .name("jun")
                .email("user@email.com")
                .password("pass")
                .build();
    }

    @Test
    public void getAccountTest() {
        // Given
        accountRepository.save(account);

        final AccountLoginDto accountLoginDto = AccountLoginDto.builder()
                .email("user@email.com")
                .password("pass")
                .build();

        // When
        final AccountInfoDto accountInfoDto = accountService.getAccount(accountLoginDto);

        // Then
        assertThat(accountInfoDto).isEqualToComparingOnlyGivenFields(accountLoginDto, "email");
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getAccountFailTest() {
        // Expected
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("wrong password");

        // Given
        accountRepository.save(account);

        final AccountLoginDto accountLoginDto = AccountLoginDto.builder()
                .email("user@email.com")
                .password("abc")
                .build();

        // When
        accountService.getAccount(accountLoginDto);
    }

    @Test
    public void createAccountTest() {
        // Given
        accountRepository.save(account);

        final AccountRegisterDto accountRegisterDto = AccountRegisterDto.builder()
                .name(account.getName())
                .password(account.getPassword())
                .email("new_user@email.com")
                .build();

        // When
        final AccountInfoDto accountInfoDto = accountService.createAccount(accountRegisterDto);

        // Then
        assertThat(accountInfoDto.getEmail()).isEqualTo(accountRegisterDto.getEmail());
        assertThat(accountInfoDto.getName()).isEqualTo(accountRegisterDto.getName());
    }

    @Test
    public void createAccountFailTest() {
        // Expected
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("invalid email");

        // Given
        accountRepository.save(account);

        final AccountRegisterDto accountRegisterDto = AccountRegisterDto.builder()
                .name(account.getName())
                .password(account.getPassword())
                .email(account.getEmail())
                .build();

        // When
        accountService.createAccount(accountRegisterDto);
    }
}
