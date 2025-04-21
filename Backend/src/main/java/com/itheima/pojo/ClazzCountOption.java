package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName:ClazzCountOption
 * Package:com.itheima.pojo
 * Description:
 *
 * @date:2025-04-10 11:03 p.m.
 * @author:Qss
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzCountOption {
    private List clazzList; //职位列表
    private List dataList; //人数列表
}
