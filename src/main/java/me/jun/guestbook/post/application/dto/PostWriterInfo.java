package me.jun.guestbook.post.application.dto;

import lombok.*;
import me.jun.guestbook.guest.application.dto.GuestResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class PostWriterInfo {

    private Long id;

    private String name;

    private String email;

    public static PostWriterInfo from(GuestResponse guestResponse) {
        return PostWriterInfo.builder()
                .id(guestResponse.getId())
                .name(guestResponse.getName())
                .email(guestResponse.getEmail())
                .build();
    }
}
