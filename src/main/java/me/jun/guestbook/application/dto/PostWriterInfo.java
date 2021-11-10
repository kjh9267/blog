package me.jun.guestbook.application.dto;

import lombok.*;
import me.jun.member.application.dto.MemberResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class PostWriterInfo {

    private Long id;

    private String name;

    private String email;

    public static PostWriterInfo from(MemberResponse memberResponse) {
        return PostWriterInfo.builder()
                .id(memberResponse.getId())
                .name(memberResponse.getName())
                .email(memberResponse.getEmail())
                .build();
    }
}
