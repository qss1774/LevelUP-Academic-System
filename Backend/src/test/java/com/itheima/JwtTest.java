package com.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:JwtTest
 * Package:com.itheima
 * Description:
 *
 * @date:2025-04-11 2:58 p.m.
 * @author:Qss
 */


public class JwtTest {

    /**
     * 添加令牌
     */
    @Test
    public void testGenerateJwt(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id",1);
        dataMap.put("username","admin");

        String jwt = Jwts.builder()
//                指定加密算法，密钥
                .signWith(SignatureAlgorithm.HS256,"aXRoZWltYQ==")
//                添加自定义信息
                .addClaims(dataMap)
//                设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
//                生成令牌
                .compact();
        System.out.println(jwt);
    }

    /**
     * 解析令牌
     */
    @Test
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc0NDQwMjI0M30.9XTpsjgnyck-xtFFgSqtYaNLF1Omb7qY5rkpFc1ork8";
        Claims claims = Jwts.parser()
//                指定密钥
                .setSigningKey("aXRoZWltYQ==")
//                解析令牌
                .parseClaimsJws(token)
//                获取自定义信息
                .getBody();
        System.out.println(claims);
    }
}
