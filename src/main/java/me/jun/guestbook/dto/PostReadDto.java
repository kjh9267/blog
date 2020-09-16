package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
public class PostReadDto {

    private final Long id;
}
