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
public class PostRequest {

    private final Long id;

    private final String title;

    private final String content;

    public Post toEntity() {
        return Post.builder()
                .id(id)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
