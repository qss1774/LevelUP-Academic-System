package com.itheima.fliter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * ClassName:DemoFilter
 * Package:com.itheima.fliter
 * Description:
 *
 * @date:2025-04-11 4:00 p.m.
 * @author:Qss
 */

//@WebFilter(urlPatterns = "/*")//拦截所有请求
@Slf4j
public class DemoFilter implements Filter {

//    初始化方法，web服务器启动时会执行一次
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init 初始化");
        Filter.super.init(filterConfig);
    }

//    业务方法，拦截到请求之后执行，会执行多次
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("拦截到请求，开始执行业务方法");
//        放行
        filterChain.doFilter(servletRequest, servletResponse);

    }

//    销毁，web服务器关闭时执行一次（资源释放环境清理）
    @Override
    public void destroy() {
        log.info("destroy 销毁");
        Filter.super.destroy();
    }
}
