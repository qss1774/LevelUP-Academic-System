package com.itheima.pojo;

/**
 * ClassName:PageResult
 * Package:com.itheima.pojo
 * Description:
 *
 * @date:2025-04-03 11:42 p.m.
 * @author:Qss
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long total;//总记录数
    private List<T> rows;
}
