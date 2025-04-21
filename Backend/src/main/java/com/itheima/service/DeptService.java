package com.itheima.service;

import com.itheima.pojo.Dept;

import java.util.List;

/**
 * ClassName:DeptService
 * Package:com.itheima.service
 * Description:
 *
 * @date:2025-03-31 10:31 p.m.
 * @author:Qss
 */


public interface DeptService {
    /**
     * 查询所有部门数据
     * @return
     */
    List<Dept> findAll();

    /**
     * 根据id删除部门
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    void save(Dept dept);

    /**
     * 根据id查询部门数据
     * @param id
     * @return
     */
    Dept getById(Integer id);

    /**
     * 修改部门
     * @param dept
     */
    void update(Dept dept);
}
