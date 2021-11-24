package me.jun.common.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BlogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestMethod = request.getMethod();

//        if (!isGetMethod(requestMethod)) {
//            // not admin ?
//            return false;
//        }

        return true;
    }

    private boolean isGetMethod(String requestMethod) {
        return HttpMethod.GET.matches(requestMethod);
    }
}
