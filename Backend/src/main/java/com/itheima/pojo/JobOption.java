package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName:JobOption
 * Package:com.itheima.pojo
 * Description:
 *
 * @date:2025-04-09 1:36 p.m.
 * @author:Qss
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOption {

    private List jobList;
    private List dataList;
}
