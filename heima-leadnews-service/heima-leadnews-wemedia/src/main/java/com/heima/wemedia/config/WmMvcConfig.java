package com.heima.wemedia.config;

import com.heima.wemedia.interceptor.WmUserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WmMvcConfig implements WebMvcConfigurer {
    //启动设置的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WmUserInterceptor()).addPathPatterns("/**");
    }
}
