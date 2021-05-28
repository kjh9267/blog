package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.post.domain.Post;
import org.springframework.data.domain.Page;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
public class ManyPostResponseDto {

    private final Page<PostResponse> postResponses;

    public static ManyPostResponseDto from(Page<Post> posts) {
        return ManyPostResponseDto.builder()
                .postResponses(posts.map(PostResponse::from))
                .build();
    }

    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Builder
    @ToString
    public static class PostResponse {

        private final String title;

        private final String content;

        public static PostResponse from(Post post) {
            return PostResponse.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .build();
        }
    }
}
