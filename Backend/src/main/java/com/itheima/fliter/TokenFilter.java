package com.itheima.fliter;

import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import com.itheima.utils.CurrentHolder;

import java.io.IOException;

/**
 * ClassName:TokenFilter
 * Package:com.itheima.fliter
 * Description:
 *
 * @date:2025-04-11 7:37 p.m.
 * @author:Qss
 */

@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //        1.获取请求路径
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI(); // /employee/login

//        2.判断是否是登陆请求，如果路径包含/login，说明是登陆操作，需要放行
        if (requestURI.contains("/login")){
            filterChain.doFilter(request, response);
            return;
        }

//        3.获取请求中的token/令牌
        String token = request.getHeader("token");

//        4.判断这个token是否存在，如果不存在，直接返回401状态码，
        if (token == null || token.isEmpty()){
            log.info("令牌为空，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
//        5.如果token存在，校验令牌，如果校验失败则返回错误信息-401状态码
        try {
            Claims claims = JwtUtils.parseJWT(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);//存入
            log.info("当前员工id为：{},将其存入Threadlocal", empId);

        }catch (Exception e){
            log.info("令牌非法，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
//        6.校验通过，放行
        log.info("令牌合法，放行");
        filterChain.doFilter(request, response);

//        7.删除Threadlocal中的数据
        CurrentHolder.remove();
    }
}
