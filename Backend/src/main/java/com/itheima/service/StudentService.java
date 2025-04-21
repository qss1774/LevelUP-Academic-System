package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;

import java.util.List;

/**
 * ClassName:StudentService
 * Package:com.itheima.service
 * Description:
 *
 * @date:2025-04-10 2:13 a.m.
 * @author:Qss
 */


public interface StudentService {
    /**
     * 分页条件查询
     * @param studentQueryParam
     * @return
     */
    PageResult page(StudentQueryParam studentQueryParam);

    /**
     * 新增学生
     * @param student
     */
    void save(Student student);

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    Student getInfo(Integer id);

    /**
     * 修改学生信息
     * @param student
     */
    void update(Student student);

    /**
     * 删除学生信息
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 扣分
     * @param id
     * @param score
     */
    void violationHandle(Integer id, Integer score);
}
