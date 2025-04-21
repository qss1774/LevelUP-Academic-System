package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName:EmpMapper
 * Package:com.itheima.mapper
 * Description:
 *
 * @date:2025-04-03 10:37 p.m.
 * @author:Qss
 */

@Mapper
public interface EmpMapper {
//    --------------原始分页查询方式-----------
//    /**
//     * 查询总记录数
//     * @return
//     */
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public Long count();
//
//    /**
//     * 分页查询
//     * start是起始索引，page是页码
//     * @return
//     */
//    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id " +
//            " order by e.update_time desc limit #{start}, #{pageSize}")
//    public List<Emp> list(Integer start, Integer pageSize);
    /**
     * 条件查询员工信息
     */
//    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc")
//    public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);
    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工基本信息
     * @options获取到生成的主键
     * @param emp
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "values (#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);

    /**
     * 根据id批量删除员工的基本信息
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据id查询员工信息以及工作经历信息
     * @param id
     * @return
     */
    Emp getById(Integer id);

    /**
     * 根据id更新员工的基本信息
     * @param emp
     */
    void updateById(Emp emp);

    /**
     * 统计员工职位人数
     * @return
     */
    @MapKey("pos")
    List<Map<String, Object>> countEmpJobData();

    /**
     * 统计员工性别
     * @return
     */
    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();

    /**
     * 查询所有员工信息
     * @return
     */
    @Select("select id, username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time from emp")
    List<Emp> findAll();

    /**
     * 根据用户名和密码查询员工
     * @param emp
     * @return
     */
    @Select("select id, username, name from emp where username = #{username} and password = #{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
