package me.jun.core.guestbook.application.dto;

import lombok.*;
import me.jun.core.guestbook.domain.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
public class CommentUpdateRequest {

    @NotNull
    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long postId;

    @NotBlank
    private String content;

    private Long writerId;
}
