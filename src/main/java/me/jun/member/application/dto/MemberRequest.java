package me.jun.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.member.domain.Member;
import me.jun.member.domain.Password;
import me.jun.member.domain.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static me.jun.member.domain.Role.USER;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
public class MemberRequest {

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
