package me.jun.guestbook.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
public class ManyPostRequestDto {

    @NotBlank
    private final int page;
}
