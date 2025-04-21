package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:DeptController
 * Package:com.itheima.controller
 * Description:
 *
 * @date:2025-03-31 10:32 p.m.
 * @author:Qss
 */
@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

//    private static final Logger log = LoggerFactory.getLogger(DeptController.class);
    @Autowired
    private DeptService deptService;
//    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    @GetMapping
    public Result list(){
//        System.out.println("查询成功");
        log.info("查询所有部门信息");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 删除部门
     * 方式一：httpservletrequest 获取请求参数
     * @param request
     * @return
     */
//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request){
//        String idStr = request.getParameter("id");
//        int id = Integer.parseInt(idStr);
//        System.out.println("根据id删除 = " + id);
//        return Result.success();
//    }

    /**
     * 删除部门
     * 方式二：@RequestParam，声明时请求参数必须传递，不传递会报错
     * @param deptId
     * @return
     */
//    @DeleteMapping("/depts")
//    public Result delete(@RequestParam(value = "id", required = false) Integer deptId){
//        System.out.println("根据id删除 = " + deptId);
//        return Result.success();
//    }

    /**
     * 删除部门
     * 方式三：省略@RequestParam，当前端传递的请求参数名与服务端方法的形参名一致则可以省略
     * @param id
     * @return
     */
    @Log
    @DeleteMapping
    public Result delete(Integer id){
//        System.out.println("根据id删除 = " + id);
        log.info("根据id删除部门：{}", id);
        //增删改不需要返回值
        deptService.deleteById(id);
        return Result.success();
    }

    /**
     * 新增部门
     * @param dept
     * @return
     */
    @Log
    @PostMapping
    public Result save(@RequestBody Dept dept){
//        System.out.println("新增部门：" + dept);
        log.info("新增部门：{}", dept);
        deptService.save(dept);
        return Result.success();
    }
    /**
     * 根据id查询部门（用于修改数据时页面回显）
     * @param id
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
//        System.out.println("根据id查询部门:" + id);
        log.info("根据id查询部门：{}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
//        System.out.println("修改部门" + dept);
        log.info("修改部门：{}", dept);
        deptService.update(dept);
        return Result.success();
    }



}
