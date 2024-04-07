package com.heima.wemedia.config;

import com.heima.wemedia.interceptor.WmUserInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WmMvcConfig implements WebMvcConfigurer {
    //启动设置的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WmUserInterceptor()).addPathPatterns("/**");
    }
}
