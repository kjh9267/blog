package me.jun.guestbook.guest.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.guestbook.guest.domain.Guest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
public class GuestRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public Guest toEntity() {
        return Guest.builder()
                .email(this.email)
                .name(this.name)
                .password(this.password)
                .build();
    }
}
