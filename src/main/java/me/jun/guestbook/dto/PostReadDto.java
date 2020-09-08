package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.domain.post.TempPost;

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

    public static PostReadDto from(TempPost tempPost) {
        return PostReadDto.builder()
                .id(tempPost.getId())
                .title(tempPost.getTitle())
                .author(tempPost.getAuthor())
                .content(tempPost.getContent())
                .date(tempPost.getDate())
                .build();
    }
}
