package me.jun.guestbook.comment.presentation.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import me.jun.guestbook.comment.domain.Comment;
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


