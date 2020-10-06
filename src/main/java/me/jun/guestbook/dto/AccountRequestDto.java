package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.domain.Account;

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
