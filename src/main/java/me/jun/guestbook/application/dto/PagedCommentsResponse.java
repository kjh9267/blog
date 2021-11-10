package me.jun.guestbook.application.dto;

import lombok.*;
import me.jun.guestbook.domain.Comment;
import org.springframework.data.domain.Page;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PagedCommentsResponse {

    private Page<CommentResponse> commentResponses;

    public static PagedCommentsResponse from(Page<Comment> comments) {
        return PagedCommentsResponse.builder()
                .commentResponses(comments.map(CommentResponse::from))
                .build();
    }
}


