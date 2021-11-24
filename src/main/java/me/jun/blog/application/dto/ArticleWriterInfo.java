package me.jun.blog.application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ArticleWriterInfo {

    @NotNull
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String name;
}
