package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jun.guestbook.domain.post.Post;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSaveDto {

    @NotBlank(message = "title is empty")
    private String title;

    @NotBlank(message = "author is empty")
    private String author;

    @NotBlank(message = "content is empty")
    private String content;

    @NotBlank(message = "password is empty")
    private String password;

    private Date date;

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
