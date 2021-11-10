package me.jun.member.application.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class MemberInfo {

    private Long id;

    public static MemberInfo from(MemberResponse memberResponse) {
        return MemberInfo.builder()
                .id(memberResponse.getId())
                .build();
    }
}
