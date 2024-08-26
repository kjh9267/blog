package me.jun.core.guestbook.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.core.guestbook.domain.Post;
import org.springframework.data.domain.Page;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedPostsResponse {

    private final Page<PostResponse> postResponses;

    public static PagedPostsResponse from(Page<Post> posts) {
        return PagedPostsResponse.builder()
                .postResponses(posts.map(PostResponse::of))
                .build();
    }

}
