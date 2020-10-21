package me.jun.guestbook.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final HttpSession session = request.getSession();
        final String requestMethod = request.getMethod();
        final String getMethod = HttpMethod.GET.name();

        if(!requestMethod.equals(getMethod) &&
                session.getAttribute("login") == null) {
            response.sendRedirect("/login");
        }

        return super.preHandle(request, response, handler);
    }
}
