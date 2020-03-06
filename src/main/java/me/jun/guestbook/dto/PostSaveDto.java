package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.domain.post.Post;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSaveDto {

    private String title;
    private String author;
    private String content;
    private Date date;
    private String password;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .author(author)
                .content(content)
                .date(date)
                .password(password)
                .build();
    }
}
