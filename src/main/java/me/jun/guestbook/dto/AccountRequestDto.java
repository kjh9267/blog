package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.account.Account;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
public class AccountRequestDto {

    private String email;

    private String name;

    private String password;

    public Account toEntity() {
        return Account.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .build();
    }
}
