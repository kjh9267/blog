package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class PostUpdateRequestDto {

    private Long id;

    private String title;

    private String content;

    private String accountEmail;

    private String password;

    public Post toEntity() {
        final Post post = Post.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .build();

        return post;
    }
}
