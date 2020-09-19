package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
public class PostInfoDto {

    private final Long id;

    private final String title;

    private final String content;

    private final Account account;

    public static PostInfoDto from(Post post) {
        return PostInfoDto.builder()
                .id(post.getId())
                .account(post.getAccount())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
