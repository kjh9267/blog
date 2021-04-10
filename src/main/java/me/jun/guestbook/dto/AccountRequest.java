package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.account.Account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
public class AccountRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public Account toEntity() {
        return Account.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .build();
    }
}
