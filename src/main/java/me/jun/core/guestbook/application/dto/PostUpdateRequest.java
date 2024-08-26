package me.jun.core.guestbook.application.dto;

import lombok.*;
import me.jun.core.guestbook.domain.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
public class PostUpdateRequest {

    @NotNull
    @Positive
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
