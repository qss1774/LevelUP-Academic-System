package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.EmpLogService;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:EmpServiceImpl
 * Package:com.itheima.service.impl
 * Description:
 *
 * @date:2025-04-03 10:39 p.m.
 * @author:Qss
 */

@Service
public class EmpServiceImpl implements EmpService {

    private static final Logger log = LoggerFactory.getLogger(EmpServiceImpl.class);
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;

    /**
     * 保存员工信息
     * @param emp
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})//事物管理 - 默认出现运行时异常RuntimeException才会回滚
    public void save(Emp emp) {
//        1.保存员工的基本信息，同时补全基础属性
        try {
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);

//        2.保存员工的工作经历信息
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
    //            遍历结合，为empId赋值
                exprList.forEach(empExpr -> {
                    empExpr.setEmpId(emp.getId());
                });
    //            批量保存
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //        记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "新增员工：" + emp);
            empLogService.insertLog(empLog);
        }
    }

    /**
     * 批量删除员工信息
     * 多次操作数据库需要加事务控制
     * @param ids
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {
//        1.删除员工的基本信息
        empMapper.deleteByIds(ids);

//        2.批量删除员工的工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

//    因为操作数据库多次需要加事务控制
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
//        1.根据ID修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

//        2.根据ID修改员工的工作经历信息
//        2.1先根据员工ID删除原有的工作经历
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));

//        2.2再添加这个员工的新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    /**
     * 查询所有员工信息
     * @return
     */
    @Override
    public List<Emp> list() {
        return empMapper.findAll();
    }

    /**
     * 员工登录
     * @param emp
     * @return
     */
    @Override
    public LoginInfo login(Emp emp) {
//        1.调用mapper接口，根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);
//        2.判断是否存在这个员工，如果不存在，直接返回null；如果存在组装登陆成功信息
        if (e != null){
            log.info("登录成功,员工信息：{}", e);
//            生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String jwt = JwtUtils.generateJwt(claims);
//            并将令牌返回给前端
            return new LoginInfo(e.getId(), e.getUsername(), e.getName(), jwt);
        }
        return null;
    }

    /**
     * 基于pagehelper进行分页查询
     * @param empQueryParam
     * @return
     */
    public PageResult<Emp> page(EmpQueryParam empQueryParam){

//        1.设置分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

//        2.执行查询
        List<Emp> empList = empMapper.list(empQueryParam);

//        3.解析查询结果并封装数据
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(), p.getResult());

    }
//    public PageResult<Emp> page(Integer page, Integer pageSize){
//    public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender,
//                                LocalDate begin, LocalDate end){
//
////        1.设置分页参数
//        PageHelper.startPage(page,pageSize);
//
////        2.执行查询
//        List<Emp> empList = empMapper.list(name, gender, begin, end);
//
////        3.解析查询结果并封装数据
//        Page<Emp> p = (Page<Emp>) empList;
//        return new PageResult<Emp>(p.getTotal(), p.getResult());
//
//    }

//    =========原始分页查询============
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//
////        1.调用mapper接口查询总记录数
//        Long total = empMapper.count();
//
////        2.调用mapper接口查询结果列表
//        Integer start = (page - 1) * pageSize;
//        List<Emp> rows = empMapper.list(start, pageSize);
//
////        3.封装结果到pageresult
//        return new PageResult<Emp>(total, rows);
//    }
}
