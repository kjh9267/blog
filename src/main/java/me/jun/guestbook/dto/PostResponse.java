package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.account.Account;
import me.jun.guestbook.domain.post.Post;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
public class PostResponse {

    private final Long id;

    private final String accountEmail;

    private final String accountName;

    private final String title;

    private final String content;

    public static PostResponse of(Post post, Account account) {
        return PostResponse.builder()
                .id(post.getId())
                .accountEmail(account.getEmail())
                .accountName(account.getName())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
