package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.domain.post.Post;
import org.springframework.data.domain.Page;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
public class ManyPostResponseDto {

    private final Page<PostResponse> postInfoDtoPage;

    public static ManyPostResponseDto from(Page<Post> posts) {
        return ManyPostResponseDto.builder()
                .postInfoDtoPage(posts.map(PostResponse::from))
                .build();

    }

    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    @Builder
    @ToString
    public static class PostResponse {

        private final Long id;

        private final String title;

        private final String content;

        public static PostResponse from(Post post) {
            return PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .build();
        }
    }
}
