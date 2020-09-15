package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.domain.post.TempPost;

import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class TempPostReadDto {

    private Long id;
    private String title;
    private String author;
    private String content;
    private Date date;

    public static TempPostReadDto from(TempPost tempPost) {
        return TempPostReadDto.builder()
                .id(tempPost.getId())
                .title(tempPost.getTitle())
                .author(tempPost.getAuthor())
                .content(tempPost.getContent())
                .date(tempPost.getDate())
                .build();
    }
}
