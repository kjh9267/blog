package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;

import java.util.List;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
public class AccountResponseDto {

    private final String name;

    private final String email;

    private final List<Post> posts;

    public static AccountResponseDto from(Account account) {
        return AccountResponseDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .posts(account.getPosts())
                .build();
    }
}
