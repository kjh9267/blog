package me.jun.core.guestbook.application.dto;

import lombok.*;
import me.jun.core.guestbook.domain.Post;
import me.jun.core.guestbook.domain.PostWriter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
public class PostCreateRequest {

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    private Long writerId;

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .postWriter(new PostWriter(this.writerId))
                .build();
    }
}
