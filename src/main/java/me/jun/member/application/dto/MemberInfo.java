package me.jun.member.application.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MemberInfo {

    private String email;

    public static MemberInfo from(String email) {
        return MemberInfo.builder()
                .email(email)
                .build();
    }
}
