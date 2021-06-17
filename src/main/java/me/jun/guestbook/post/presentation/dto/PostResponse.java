package me.jun.guestbook.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.post.domain.Post;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
public class PostResponse {

    private final Long id;

    private final String writer;

    private final String title;

    private final String content;

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
