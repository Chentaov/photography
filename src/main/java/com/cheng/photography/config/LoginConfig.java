package com.cheng.photography.config;

import com.cheng.photography.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry){
                registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").
                excludePathPatterns(
                        "/",
                        "/fly/html/user/login.html",
                        "/fly/html/user/reg.html",
                        "/fly/html/user/forget.html",
                        "/fly/html/other/notice.html",
                        "/emailTemplate.html",
                        "/alert",
                        "/login",
                        "/reg",
                        "/forget",
                        "/dologin",
                        "/dologout",
                        "/doregister",
                        "/sendEmailReset",
                        "/resetPasswordByEmail",
                        "/res/**",
                        "/photo/**",
                        "/jQueryCss3Sp/**",
                        "/img_upload/**",
                        "/uploaded_img/**",
                        "/webjars/**"
                                    );
    }
}
