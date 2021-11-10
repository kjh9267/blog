package me.jun.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jun.member.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
                .password(this.password)
                .build();
    }
}