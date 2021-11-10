package me.jun.guestbook.application.dto;

import lombok.*;
import me.jun.guestbook.domain.Post;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
public class PostCreateRequest {

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}