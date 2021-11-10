package me.jun.guestbook.application.dto;

import lombok.*;
import me.jun.member.application.dto.MemberResponse;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CommentWriterInfo {

    private Long id;

    private String name;

    private String email;

    public static CommentWriterInfo from(MemberResponse memberResponse) {
        return CommentWriterInfo.builder()
                .id(memberResponse.getId())
                .name(memberResponse.getName())
                .email(memberResponse.getEmail())
                .build();
    }
}
