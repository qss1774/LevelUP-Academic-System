package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import com.itheima.service.StudentService;
import org.apache.catalina.WebResourceRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName:StudentServiceImpl
 * Package:com.itheima.service.impl
 * Description:
 *
 * @date:2025-04-10 2:13 a.m.
 * @author:Qss
 */

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;


    /**
     * 分页条件查询
     * @param studentQueryParam
     * @return
     */
    @Override
    public PageResult page(StudentQueryParam studentQueryParam) {
//        1.设置pagehelper分页参数
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());
//        2.执行查询
        List<Student> studentList = studentMapper.list(studentQueryParam);
        System.out.println("当前查询 clazzId = " + studentQueryParam.getClazzId());

//        3.分装分页结果
        Page<Student> p = (Page<Student>) studentList;
        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 新增学生
     * @param student
     */
    @Override
    public void save(Student student) {
//        1.补充基础属性
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    @Override
    public Student getInfo(Integer id) {
        return studentMapper.getById(id);
    }

    /**
     * 修改学生信息
     * @param student
     */
    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.update(student);
    }

    /**
     * 删除学生信息
     * @param ids
     */
    @Override
    public void delete(List<Integer> ids) {
        studentMapper.delete(ids);
    }

    /**
     * 违规处理
     * @param id
     * @param score
     */
    @Override
    public void violationHandle(Integer id, Integer score) {
        studentMapper.updateViolation(id, score);
    }
}
