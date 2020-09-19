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
public class AccountInfoDto {

    private final String name;

    private final String email;

    private final List<Post> posts;

    public final AccountInfoDto from(Account account) {
        return AccountInfoDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .posts(account.getPosts())
                .build();
    }
}
