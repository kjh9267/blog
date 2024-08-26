package me.jun.core.guestbook.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import me.jun.core.guestbook.domain.Comment;
import org.springframework.data.domain.Page;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedCommentsResponse {

    private Page<CommentResponse> commentResponses;

    public static PagedCommentsResponse from(Page<Comment> comments) {
        return PagedCommentsResponse.builder()
                .commentResponses(comments.map(CommentResponse::from))
                .build();
    }
}


