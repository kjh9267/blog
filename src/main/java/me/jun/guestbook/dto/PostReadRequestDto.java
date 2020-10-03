package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class PostReadRequestDto {

    private Long id;
}
