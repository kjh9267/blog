package me.jun.guestbook.post.presentation.dto;

import lombok.*;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class WriterInfo {

    private Long id;

    private String name;

    private String email;

    public static WriterInfo from(GuestResponse guestResponse) {
        return WriterInfo.builder()
                .id(guestResponse.getId())
                .name(guestResponse.getName())
                .email(guestResponse.getEmail())
                .build();
    }
}
