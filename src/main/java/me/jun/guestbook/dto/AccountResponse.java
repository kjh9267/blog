package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.domain.account.Account;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
@ToString
public class AccountResponse {

    private final String name;

    private final String email;

    public static AccountResponse from(Account account) {
        return AccountResponse.builder()
                .name(account.getName())
                .email(account.getEmail())
                .build();
    }
}
