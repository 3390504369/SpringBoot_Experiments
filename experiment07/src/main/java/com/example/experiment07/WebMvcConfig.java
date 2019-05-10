package com.example.experiment07;

import com.example.experiment07.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//创建配置类实现接口
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    public void addInterceptors(InterceptorRegistry registry){
        //配置添加拦截器loginInterceptor
        registry.addInterceptor(loginInterceptor)
                //添加拦截路径
                .addPathPatterns("/api/**")
                //排除拦截路径
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/register");
    }
}
