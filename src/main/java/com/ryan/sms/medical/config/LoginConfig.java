package com.ryan.sms.medical.config;

import com.ryan.sms.medical.interceptor.AdminInterceptor;
import com.ryan.sms.medical.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



public class LoginConfig implements WebMvcConfigurer {
    /**
     * 不需要登录拦截的url
     */
    private final String[] notLoginInterceptPaths = {"/", "open/**", "/login","/register"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/user/**").excludePathPatterns(notLoginInterceptPaths);
//        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**").excludePathPatterns(notLoginInterceptPaths);
    }
}
