package com.itheima.mapper;

import com.itheima.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * ClassName:DeptMapper
 * Package:com.itheima.mapper
 * Description:
 *
 * @date:2025-03-31 10:30 p.m.
 * @author:Qss
 */

@Mapper
public interface DeptMapper {
    /**
     * 查询所以的部门数据
     * @return
     */
    @Select("select id, name, create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();

    /**
     * 根据id删除部门
     * @param id
     */
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    @Insert("insert into dept(name, create_time, update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    /**
     * 根据id查询部门数据
     *
     * @param id
     * @return
     */
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    /**
     * 更新部门
     * @param dept
     */
    @Update("update dept set name = #{name}, update_time=#{updateTime} where id = #{id}")
    void update(Dept dept);
}
