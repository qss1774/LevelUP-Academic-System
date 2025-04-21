package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/**
 * ClassName:EmpService
 * Package:com.itheima.service
 * Description:
 *
 * @date:2025-04-03 10:38 p.m.
 * @author:Qss
 */


public interface EmpService {
    /**
     * 分页查询(前面两个参数），后面两个参数是条件查询的参数
     * @param empQueryParam
     * @return
     */
//    PageResult<Emp> page(Integer page, Integer pageSize);
//    PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender,
//                         LocalDate begin, LocalDate end);
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 新增员工
     * @param emp
     */
    void save(Emp emp);

    /**
     * 批量删除员工信息
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    Emp getInfo(Integer id);

    /**
     * 修改员工信息
     * @param emp
     */
    void update(Emp emp);

    /**
     * 查询员工信息
     * @return
     */
    List<Emp> list();

    /**
     * 员工登录
     * @param emp
     * @return
     */
    LoginInfo login(Emp emp);
}
