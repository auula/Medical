package com.ryan.sms.medical.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        Object login_user = request.getSession().getAttribute("LOGIN_USER");
        if (login_user == null || login_user.equals("")) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
