package me.jun.guestbook.guest.presentation.dto;

import lombok.*;
import me.jun.guestbook.guest.domain.Guest;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
@ToString
public class GuestResponse {

    private final Long id;

    private final String name;

    private final String email;

    public static GuestResponse from(Guest guest) {
        return GuestResponse.builder()
                .id(guest.getId())
                .name(guest.getName())
                .email(guest.getEmail())
                .build();
    }
}
