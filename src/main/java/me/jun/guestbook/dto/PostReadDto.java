package me.jun.guestbook.dto;

import lombok.Getter;
import me.jun.guestbook.domain.post.Post;

import java.util.Date;

@Getter
public class PostReadDto {

    private Long id;
    private String title;
    private String author;
    private String content;
    private Date date;

    public PostReadDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.content = post.getContent();
        this.date = post.getDate();
    }
}
