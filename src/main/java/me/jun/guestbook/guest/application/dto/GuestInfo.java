package me.jun.guestbook.guest.application.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class GuestInfo {

    private Long id;

    public static GuestInfo from(GuestResponse guestResponse) {
        return GuestInfo.builder()
                .id(guestResponse.getId())
                .build();
    }
}
