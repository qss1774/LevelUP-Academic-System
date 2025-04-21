package com.itheima.controller;

import com.itheima.pojo.*;
import com.itheima.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ReportController
 * Package:com.itheima.controller
 * Description:
 *
 * @date:2025-04-09 2:16 p.m.
 * @author:Qss
 */

@Slf4j
//@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 统计员工职位人数
     */
    @GetMapping("/report/empJobData")
    public Result getEmpJobData()
    {
        log.info("获取员工职位数据:");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }

    /**
     * 统计员工性别数据
     */
    @GetMapping("/report/empGenderData")
    public Result getEmpGenderData()
    {
        log.info("获取员工性别数据:");
        List<Map<String,Object>> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }

    /**
     * 统计班级人数数据
     */
    @GetMapping("/report/studentCountData")
    public Result getStudentCountData(){
        log.info("获取班级人数数据:");
        ClazzCountOption clazzCountOption = reportService.getStudentCountData();
        return Result.success(clazzCountOption);

    }

    /**
     * 统计学员学历数据
     * @return
     */
    @GetMapping("/report/studentDegreeData")
    public Result getStudentDegreeData(){
        log.info("统计学员的学历信息:");
        List<Map<String,Object>> dataList = reportService.getStudentDegreeData();
        return Result.success(dataList);
    }

    /**
     * 统计操作日志
     * Controller (控制器)
     *    ↓
     * Service（业务逻辑）
     *    ↓
     * Mapper（映射接口）
     *    ↓
     * SQL（@Select语句，连表查询）
     *    ↓
     * 数据库（operate_log + emp）
     */
    @GetMapping("/log/page")
    public Result LogQuery(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
//        @RequestParam：绑定 query 参数
//
//        reportService.LogQuery(...)：调用下一层业务逻辑
//
//        Result.success(...)：将分页结果包一层，返回给前端
        PageResult<OperateLog> pageResult = reportService.LogQuery(page, pageSize);
        return Result.success(pageResult); // 这里传的还是 PageResult<OperateLog>
    }

}
