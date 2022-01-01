package com.yang.ym.config;

import com.yang.ym.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author qcy
 * @create 2021/11/12 23:05:26
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor).addPathPatterns("/**");
    }

}
