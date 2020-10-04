package me.jun.guestbook.dto;

import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostDeleteRequestDto {

    private Long id;
}
