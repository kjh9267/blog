package me.jun.guestbook.comment.presentation.dto;

import lombok.*;
import me.jun.guestbook.comment.domain.Comment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CommentResponse {

    private Long id;

    private Long writerId;

    private Long postId;

    private String content;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .writerId(comment.getWriterId())
                .postId(comment.getPostId())
                .content(comment.getContent())
                .build();
    }
}
