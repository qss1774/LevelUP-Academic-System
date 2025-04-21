package com.itheima.controller;


import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import com.itheima.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:StudentController
 * Package:com.itheima.controller
 * Description:
 *
 * @date:2025-04-10 2:11 a.m.
 * @author:Qss
 */

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页条件查询
     * @param studentQueryParam
     * @return
     */
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("分页查询，参数：{}", studentQueryParam);
        PageResult pageResult = studentService.page(studentQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 新增学生
     * @param student
     * 必须添加requesbody否则前端传过来的数据接受不到，要转换成json对象
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Student student){
        log.info("新增学员：", student);
        studentService.save(student);
        return Result.success();
    }

    /**
     * 根据id查询学员信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询学员信息: {}", id);
        Student student = studentService.getInfo(id);
        return Result.success(student);
    }

    /**
     * 修改学员信息
     * @param student
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Student student){
        log.info("修改学员信息：{}", student);
        studentService.update(student);
        return Result.success();
    }

    /**
     * 删除学员信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("根据id删除学员信息: {}", ids);
        studentService.delete(ids);
        return Result.success();
    }

    /**
     * 扣分
     * @param id
     * @param score
     * @return
     */
    @PutMapping("/violation/{id}/{score}")
    public Result violationHandle(@PathVariable Integer id, @PathVariable Integer score){
        log.info("违纪处理：{},{}", id, score);
        studentService.violationHandle(id, score);
        return Result.success();
    }

}
