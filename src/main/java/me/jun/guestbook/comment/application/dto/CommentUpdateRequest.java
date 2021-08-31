package me.jun.guestbook.comment.application.dto;

import lombok.*;
import me.jun.guestbook.comment.domain.Comment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CommentUpdateRequest {

    private Long id;

    private Long postId;

    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .id(id)
                .postId(postId)
                .content(content)
                .build();
    }
}
