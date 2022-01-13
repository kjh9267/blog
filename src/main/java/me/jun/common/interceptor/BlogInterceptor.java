package me.jun.common.interceptor;

import lombok.RequiredArgsConstructor;
import me.jun.common.MemberExtractor;
import me.jun.common.security.InvalidTokenException;
import me.jun.member.application.dto.MemberInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Enumeration;

import static me.jun.member.domain.Role.ADMIN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;

@Component
@RequiredArgsConstructor
public class BlogInterceptor extends HandlerInterceptorAdapter {

    private final MemberExtractor memberExtractor;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (isGetMethod(request)) {
            return true;
        }

        String token = request.getHeader(AUTHORIZATION);

        MemberInfo memberInfo = memberExtractor.extractMemberFrom(token);

        if (!isAdmin(memberInfo)) {
            throw new InvalidTokenException();
        }

        return true;
    }

    private boolean isGetMethod(HttpServletRequest request) {
        String method = request.getMethod();
        return GET.matches(method);
    }

    private boolean isAdmin(MemberInfo memberInfo) {
        return memberInfo.getRole().equals(ADMIN);
    }
}
