package me.jun.member.application.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MemberInfo {

    private Long id;

    private String email;

    public static MemberInfo from(MemberResponse memberResponse) {
        return MemberInfo.builder()
                .id(memberResponse.getId())
                .email(memberResponse.getEmail())
                .build();
    }
}
