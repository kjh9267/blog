package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.Post;
import org.springframework.data.domain.Page;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
public class ManyPostResponseDto {

    private final Page<PostResponseDto> postInfoDtoPage;

    public static ManyPostResponseDto from(Page<Post> posts) {
        return ManyPostResponseDto.builder()
                .postInfoDtoPage(posts.map(PostResponseDto::from))
                .build();

    }
}
