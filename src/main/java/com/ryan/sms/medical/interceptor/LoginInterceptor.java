package com.ryan.sms.medical.interceptor;


import com.ryan.sms.medical.utils.JsonData;
import com.ryan.sms.medical.utils.JsonUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Integer user_type = (Integer) request.getSession().getAttribute("USER_TYPE");
        Object login_user = request.getSession().getAttribute("LOGIN_USER");
        if (login_user == null || login_user.equals("")) {
            response.sendRedirect("/");
            return false;
        }else {
            if (login_user!=null && user_type == 1){
                return true;
            }else {
                JsonUtil.outJson(response,new JsonData().build(-100,"管理员不可以直接操作普通用户页面！！！"));
            }
        }
        return false;
    }
}
