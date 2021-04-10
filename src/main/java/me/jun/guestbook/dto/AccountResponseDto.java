package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.domain.account.Account;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
@ToString
public class AccountResponseDto {

    private final String name;

    private final String email;

    public static AccountResponseDto from(Account account) {
        return AccountResponseDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .build();
    }
}
