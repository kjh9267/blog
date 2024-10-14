package me.jun.core.member.application.dto;

import lombok.*;
import me.jun.core.member.domain.Role;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MemberInfo {

    private Long id;

    private String email;

    private Role role;

    public static MemberInfo from(MemberResponse memberResponse) {
        return MemberInfo.builder()
                .id(memberResponse.getId())
                .email(memberResponse.getEmail())
                .role(memberResponse.getRole())
                .build();
    }
}
