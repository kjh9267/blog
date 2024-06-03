package me.jun.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.member.application.dto.MemberRequest;
import me.jun.member.application.dto.MemberResponse;
import me.jun.member.application.dto.TokenResponse;
import me.jun.member.domain.Member;
import me.jun.member.domain.Password;

import static me.jun.member.domain.Role.USER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class MemberFixture {

    public static final String ACCESS_TOKEN = "access_token";

    public static final Long MEMBER_ID = 1L;

    public static final String MEMBER_NAME = "test user";

    public static final String ADMIN_NAME = "junho";

    public static final String EMAIL = "testuser@email.com";

    public static final String ADMIN_EMAIL = "admin@admin.com";

    public static final String PASSWORD = "pass";

    public static final String ADMIN_PASSWORD = "123";

    public static final String JWT = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlckBlbWFpbC5jb20iLCJpYXQiOjE2Mjk4ODAxMzQsImV4cCI6MTYyOTg4MTkzNH0.k2TUffQCtLTaWJ71JMCU_052ekg32sOsO4tJw8726cQ";

    public static Member member() {
        return Member.builder()
                .id(MEMBER_ID)
                .name(MEMBER_NAME)
                .password(new Password(PASSWORD))
                .email(EMAIL)
                .role(USER)
                .build();
    }

    public static MemberRequest adminRequest() {
        return MemberRequest.builder()
                .name(ADMIN_NAME)
                .password(ADMIN_PASSWORD)
                .email(ADMIN_EMAIL)
                .build();
    }

    public static MemberRequest memberRequest() {
        return MemberRequest.builder()
                .name(MEMBER_NAME)
                .password(PASSWORD)
                .email(EMAIL)
                .build();
    }

    public static MemberResponse memberResponse() {
        return MemberResponse.from(member());
    }

    public static TokenResponse tokenResponse() {
        return TokenResponse.from(JWT);
    }
}
