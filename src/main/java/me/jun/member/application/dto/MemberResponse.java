package me.jun.member.application.dto;

import lombok.*;
import me.jun.member.domain.Member;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder
@ToString
public class MemberResponse {

    private final Long id;

    private final String name;

    private final String email;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
