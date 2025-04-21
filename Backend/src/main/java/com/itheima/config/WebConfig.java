package com.itheima.config;

import com.itheima.interceptor.DemonInterceptor;
import com.itheima.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName:WebConfig
 * Package:com.itheima.config
 * Description:
 *
 * @date:2025-04-11 8:36 p.m.
 * @author:Qss
 */

/**
 * 拦截器配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{

//    @Autowired
//    private DemonInterceptor demonInterceptor;

//    @Autowired
//    private TokenInterceptor tokenInterceptor;

//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor)
//                .addPathPatterns("/**")//拦截所有请求
//                .excludePathPatterns("/login");//放行登录
//    }
}
