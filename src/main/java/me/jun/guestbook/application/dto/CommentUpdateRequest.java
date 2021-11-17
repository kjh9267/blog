package me.jun.guestbook.application.dto;

import lombok.*;
import me.jun.guestbook.domain.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CommentUpdateRequest {

    @NotNull
    private Long id;

    @NotNull
    private Long postId;

    @NotBlank
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .id(id)
                .postId(postId)
                .content(content)
                .build();
    }
}
