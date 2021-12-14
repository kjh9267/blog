package me.jun.member.application.dto;

import lombok.*;
import me.jun.member.domain.Member;
import me.jun.member.domain.Role;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@ToString
public class MemberResponse {

    private final Long id;

    private final String name;

    private final String email;

    private final Role role;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }
}
