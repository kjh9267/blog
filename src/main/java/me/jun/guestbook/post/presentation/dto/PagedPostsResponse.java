package me.jun.guestbook.post.presentation.dto;

import lombok.*;
import me.jun.guestbook.post.domain.Post;
import org.springframework.data.domain.Page;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
public class PagedPostsResponse {

    private final Page<PostResponse> postResponses;

    public static PagedPostsResponse from(Page<Post> posts) {
        return PagedPostsResponse.builder()
                .postResponses(posts.map(PostResponse::of))
                .build();
    }

}
