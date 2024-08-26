package me.jun.core.member.application.dto;

import lombok.*;
import me.jun.core.member.domain.Role;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MemberInfo {

    private String email;

    private Role role;

    public static MemberInfo from(String email, Role role) {
        return MemberInfo.builder()
                .email(email)
                .role(role)
                .build();
    }
}
