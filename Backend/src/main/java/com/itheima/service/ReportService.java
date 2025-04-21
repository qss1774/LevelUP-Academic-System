package com.itheima.service;

import com.itheima.pojo.ClazzCountOption;
import com.itheima.pojo.JobOption;
import com.itheima.pojo.OperateLog;
import com.itheima.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ReportService
 * Package:com.itheima.service
 * Description:
 *
 * @date:2025-04-09 2:18 p.m.
 * @author:Qss
 */


public interface ReportService {

    /**
     * 统计员工职位人数
     * @return
     */
    JobOption getEmpJobData();

    /**
     * 统计员工性别
     * @return
     */
    List<Map<String, Object>> getEmpGenderData();

    /**
     * 统计班级学生人数
     * @return
     */
    ClazzCountOption getStudentCountData();

    /**
     * 统计学生学历
     * @return
     */
    List<Map<String, Object>> getStudentDegreeData();

    /**
     * 统计日志
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<OperateLog> LogQuery(int page, int pageSize);
}
