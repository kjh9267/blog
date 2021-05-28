package me.jun.guestbook.dto;

import lombok.*;
import me.jun.guestbook.domain.guest.Guest;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
@ToString
public class GuestResponse {

    private final String name;

    private final String email;

    public static GuestResponse from(Guest guest) {
        return GuestResponse.builder()
                .name(guest.getName())
                .email(guest.getEmail())
                .build();
    }
}
