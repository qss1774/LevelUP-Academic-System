package com.itheima.service;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import com.itheima.pojo.PageResult;

import java.util.List;

/**
 * ClassName:ClazzService
 * Package:com.itheima.service.impl
 * Description:
 *
 * @date:2025-04-09 11:33 p.m.
 * @author:Qss
 */


public interface ClazzService {
    /**
     * 分页
     * @param clazzQueryParam
     * @return
     */
    PageResult page(ClazzQueryParam clazzQueryParam);

    /**
     * 删除班级
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 添加班级
     * @param clazz
     */
    void save(Clazz clazz);

    /**
     * 根据id查询班级的详细信息
     * @param id
     * @return
     */
    Clazz getInfo(Integer id);

    /**
     * 更新班级信息
     * @param clazz
     */
    void update(Clazz clazz);

    /**
     * 查询所有班级信息
     * @return
     */
    List<Clazz> list();
}
