package me.jun.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.domain.Account;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Getter
public class PostUpdateRequestDto {

    private final Long id;

    private final String title;

    private final String content;

    private final Account account;
}
