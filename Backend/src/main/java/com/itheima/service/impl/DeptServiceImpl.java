package com.itheima.service.impl;

import com.itheima.mapper.DeptMapper;
import com.itheima.pojo.Dept;
import com.itheima.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName:DeptServiceImpl
 * Package:com.itheima.service.impl
 * Description:
 *
 * @date:2025-03-31 10:31 p.m.
 * @author:Qss
 */

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void save(Dept dept) {
//        1.补全基础属性-createtime和updatetime
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

//        2.调用mapper接口方法操作数据
        deptMapper.insert(dept);
    }

    @Override
    public Dept getById(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void update(Dept dept) {
//        1.补全基础属性-updatetime
        dept.setUpdateTime(LocalDateTime.now());

//        2.调用mapper接口方法更新部门数据
        deptMapper.update(dept);
    }
}
