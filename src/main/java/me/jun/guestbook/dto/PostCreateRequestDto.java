package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.post.Post;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
public class PostCreateRequestDto {

    private final String title;

    private final String content;

    private final String accountEmail;

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
