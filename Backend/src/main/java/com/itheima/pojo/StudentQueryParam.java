package com.itheima.pojo;

import lombok.Data;

/**
 * ClassName:StudentQueryParam
 * Package:com.itheima.pojo
 * Description:
 *
 * @date:2025-04-10 10:18 a.m.
 * @author:Qss
 */

@Data
public class StudentQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String name;
    private Integer degree;
    private Integer clazzId;
}
