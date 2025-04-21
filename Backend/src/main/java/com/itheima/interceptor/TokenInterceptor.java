package com.itheima.interceptor;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ClassName:TokenInterceptor
 * Package:com.itheima.interceptor
 * Description:
 *
 * @date:2025-04-11 8:51 p.m.
 * @author:Qss
 */

@Slf4j
//@Component
public class TokenInterceptor implements HandlerInterceptor {

//    校验令牌的合法性需要在目标资源运行之前进行校验

//    过滤器中的放行需要调用dofilter；
//    拦截器中的放行需要返回值，因为是boolean类型

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        //        1.获取请求路径
//        String requestURI = request.getRequestURI(); // /employee/login
//
////        2.判断是否是登陆请求，如果路径包含/login，说明是登陆操作，需要放行
//        if (requestURI.contains("/login")){
////            过滤器中的放行
////            filterChain.doFilter(request, response);
//
////            拦截器中的放行直接return true
//            return true;
//        }

//        3.获取请求中的token/令牌
        String token = request.getHeader("token");

//        4.判断这个token是否存在，如果不存在，直接返回401状态码，
        if (token == null || token.isEmpty()){
            log.info("令牌为空，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
//        5.如果token存在，校验令牌，如果校验失败则返回错误信息-401状态码
        try {
            JwtUtils.parseJWT(token);
        }catch (Exception e){
            log.info("令牌非法，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
//        6.校验通过，放行
        log.info("令牌合法，放行");
        return true;

    }
}
