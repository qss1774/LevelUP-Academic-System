package com.itheima.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:DemonInterceptor
 * Package:com.itheima.interceptor
 * Description:
 *
 * @date:2025-04-11 8:32 p.m.
 * @author:Qss
 */

@Slf4j
//@Component
public class DemonInterceptor implements HandlerInterceptor {
//    在目标资源方法运行之前执行 - 返回值：true放行，false拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截到请求，开始执行业务方法");
        return true;
    }

//    在目标资源方法运行之后，视图渲染之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

//    视图渲染完成之后执行-前后端分离不需要
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("视图渲染完成，开始执行清理工作");
    }
}
