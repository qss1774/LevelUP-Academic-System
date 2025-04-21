package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName:EmpController
 * Package:com.itheima.controller
 * Description:
 *
 * @date:2025-04-03 10:40 p.m.
 * @author:Qss
 */

/**
 * 员工管理
 */
@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;
    /**
     * 分页查询(前两个参数是分页查询参数，后面的参数是条件查询参数)
     * 其中日期时间类型参数接受时，需要使用@DateTimeFormat注解指定前端传递的日期格式
     * @return
     */
//    @GetMapping
//    public Result page(@RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer pageSize,
//
//        log.info("分页查询: {},{}", page,pageSize);
//        PageResult<Emp> pageResult = empService.page(page,pageSize);
//        return Result.success(pageResult);

//    @GetMapping
//    public Result page(@RequestParam(defaultValue = "1") Integer page,
//                       @RequestParam(defaultValue = "10") Integer pageSize,
//                       String name, Integer gender,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
//                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
//        log.info("分页查询: {},{},{},{},{},{}", page,pageSize,name,gender,begin,end);
////        这四个参数还需要继续往下传递，给service
//        PageResult<Emp> pageResult = empService.page(page,pageSize,name,gender,begin,end);
//        return Result.success(pageResult);
//
//    }
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询: {}", empQueryParam);
//        这四个参数还需要继续往下传递，给service
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 新增员工
     * 增删改不需要返回值
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("新增员工: {}", emp);
        empService.save(emp);
        return Result.success();
    }

//    /**
//     * 删除员工 - 数组
//     * @param ids
//     * @return
//     */
//    @DeleteMapping
//    public Result delete(Integer[] ids){
//        log.info("根据id删除员工: {}", Arrays.toString(ids));
//        empService.delete(ids);
//        return Result.success();
//
//    }

    /**
     * 删除员工 - List
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("根据id删除员工: {}", ids);
        empService.delete(ids);
        return Result.success();
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询员工信息: {}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    /**
     * 修改员工信息
     * @param emp
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工信息: {}", emp);
        empService.update(emp);
        return Result.success();
    }

    /**
     * 查询所有员工数据
     * @return
     */
    @GetMapping("/list")
    public Result list(){
        log.info("查询所有的员工数据");
        List<Emp> empList = empService.list();
        return Result.success(empList);
    }
}
