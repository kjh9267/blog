package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
public class PostUpdateRequestDto {

    private final Long id;

    private final String title;

    private final String content;

    private final Account account;

    public Post toEntity() {
        final Post post = Post.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .build();

        account.addPost(post);

        return post;
    }
}
