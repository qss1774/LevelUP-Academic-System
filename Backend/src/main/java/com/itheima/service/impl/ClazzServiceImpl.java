package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ClazzMapper;
import com.itheima.mapper.StudentMapper;
import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.service.ClazzService;
import org.apache.catalina.WebResourceRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName:ClazzServiceImpl
 * Package:com.itheima.service.impl
 * Description:
 *
 * @date:2025-04-09 11:33 p.m.
 * @author:Qss
 */

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 分页查询
     * @param clazzQueryParam
     * @return
     */
    @Override
    public PageResult page(ClazzQueryParam clazzQueryParam) {
//        1.设置pagehelper分页参数
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
//        2.执行查询
        List<Clazz> clazzList = clazzMapper.list(clazzQueryParam);
//        3.封装分页结果
        Page<Clazz> p = (Page<Clazz>)clazzList;
        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 根据id删除班级
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
//        1.查询班级是否有学员
        Integer count = studentMapper.countByClazzId(id);
        if (count > 0){
            throw new RuntimeException("该班级有学员，不能删除");
        }
        clazzMapper.deleteById(id);
    }

    /**
     * 新增班级
     * @param clazz
     */
    @Override
    public void save(Clazz clazz) {
//        1.补全基础属性
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());

//        2.保存班级基本信息
        clazzMapper.insert(clazz);
    }

    /**
     * 根据id查询班级信息
     * @param id
     * @return
     */
    @Override
    public Clazz getInfo(Integer id) {
        return clazzMapper.getById(id);
    }

    /**
     * 更新班级信息
     * @param clazz
     */
    @Override
    public void update(Clazz clazz) {
//        根据id更新班级基本信息
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateById(clazz);
    }

    /**
     * 查询班级列表
     * @return
     */
    @Override
    public List<Clazz> list() {
        return clazzMapper.findAll();
    }
}
