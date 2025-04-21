package com.itheima.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.mapper.EmpMapper;
import com.itheima.mapper.OperateLogMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.ClazzCountOption;
import com.itheima.pojo.JobOption;
import com.itheima.pojo.OperateLog;
import com.itheima.pojo.PageResult;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ReportServiceImpl
 * Package:com.itheima.service.impl
 * Description:
 *
 * @date:2025-04-09 2:18 p.m.
 * @author:Qss
 */

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public JobOption getEmpJobData() {
//        1.调用mapper接口获取统计数据
//        map: pos=教研主管, num = 1
//        map: pos=行政主管, num = 2
        List<Map<String, Object>> list = empMapper.countEmpJobData();
//        2.组装结果并返回
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();

        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public ClazzCountOption getStudentCountData() {
        List<Map<String, Object>> countList = studentMapper.getStudentCount();
        if (!CollectionUtils.isEmpty(countList)){
            List<Object> clazzList = countList.stream().map(map ->
            {return map.get("cname");}).toList();

            List<Object> dataList = countList.stream().map(map ->
            {return map.get("scount");}).toList();

            return new ClazzCountOption(clazzList, dataList);
        }

        return null;
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        return studentMapper.countStudentDegreeData();
    }

    @Override
    public PageResult<OperateLog> LogQuery(int page, int pageSize) {
//PageHelper.startPage(...)：告诉分页插件从哪页查、查多少条
//
//PageInfo：分页工具类，会自动封装 total、list 等
//
//PageResult：你自定义的分页响应结构
        PageHelper.startPage(page, pageSize);//开启分页拦截
        List<OperateLog> list = operateLogMapper.findByPage();//查询全部信息
        PageInfo<OperateLog> pageInfo = new PageInfo<>(list);//封装分页信息
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());//返回分页结果，total和list
    }
}
