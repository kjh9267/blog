package me.jun.core.guestbook.application.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class PostDeleteRequest {

    private Long postId;

    private Long writerId;

    public static PostDeleteRequest of(Long postId, Long writerId) {
        return PostDeleteRequest.builder()
                .postId(postId)
                .writerId(writerId)
                .build();
    }
}
