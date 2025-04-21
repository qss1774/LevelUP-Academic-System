package com.itheima.pojo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * ClassName:ClazzQueryParam
 * Package:com.itheima.pojo
 * Description:
 *
 * @date:2025-04-09 11:38 p.m.
 * @author:Qss
 */

@Data
public class ClazzQueryParam {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}
