package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.domain.post.Post;

import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class PostReadDto {

    private Long id;
    private String title;
    private String author;
    private String content;
    private Date date;

    public static PostReadDto from(Post post) {
        return PostReadDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .content(post.getContent())
                .date(post.getDate())
                .build();
    }
}
