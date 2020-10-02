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
public class PostResponseDto {

    private final Long id;

    private final String title;

    private final String content;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
