package me.jun.member.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import me.jun.member.domain.Member;
import me.jun.member.domain.Role;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberResponse extends RepresentationModel<MemberResponse> {

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
