package me.jun.member.application.dto;

import lombok.*;
import me.jun.member.domain.Member;
import me.jun.member.domain.Password;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static me.jun.member.domain.Role.USER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class RegisterRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .name(this.name)
                .password(new Password(password))
                .role(USER)
                .build();
    }
}