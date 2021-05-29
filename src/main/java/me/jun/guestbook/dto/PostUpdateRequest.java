package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.post.domain.Post;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
public class PostUpdateRequest {

    @NotBlank
    private final Long id;

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    public Post toEntity() {
        return Post.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
