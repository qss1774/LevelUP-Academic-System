package com.itheima.mapper;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName:ClazzMapper
 * Package:com.itheima.mapper
 * Description:
 *
 * @date:2025-04-09 11:33 p.m.
 * @author:Qss
 */

@Mapper
public interface ClazzMapper {

    /**
     * 分页动态查询
     * @param clazzQueryParam
     * @return
     */
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    /**
     * 新增班级
     * @param clazz
     */
    void insert(Clazz clazz);

    /**
     * 根据id查询班级
     * @param id
     * @return
     */
    Clazz getById(Integer id);

    /**
     * 修改班级
     * @param clazz
     */
    void updateById(Clazz clazz);

    /**
     * 删除班级
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有班级
     * @return
     */
    List<Clazz> findAll();
}
