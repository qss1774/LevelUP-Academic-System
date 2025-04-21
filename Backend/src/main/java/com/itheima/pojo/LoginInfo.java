package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:LoginInfo
 * Package:com.itheima.pojo
 * Description:
 *
 * @date:2025-04-11 12:41 p.m.
 * @author:Qss
 */

/**
 * 登录信息
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
    private Integer id;
    private String username;
    private String name;
    private String token;
}
