package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.post.Post;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
public class PostRequest {

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
