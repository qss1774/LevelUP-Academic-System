package com.itheima.mapper;

import com.itheima.pojo.Student;
import com.itheima.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * ClassName:StudentMapper
 * Package:com.itheima.mapper
 * Description:
 *
 * @date:2025-04-10 2:11 a.m.
 * @author:Qss
 */

@Mapper
public interface StudentMapper {

    /**
     * 根据班级id查询学生数量
     * @param id
     * @return
     */
    @Select("select count(*) from student where clazz_id = #{id}")
    Integer countByClazzId(Integer id);

    /**
     * 分页查询
     * @param studentQueryParam
     * @return
     */
    List<Student> list(StudentQueryParam studentQueryParam);

    /**
     * 新增学生
     * @param student
     */
    @Insert("insert into student(name, no, gender, phone, id_card, is_college, address, degree, graduation_date, clazz_id, create_time, update_time) VALUES " +
    "(#{name}, #{no}, #{gender}, #{phone}, #{idCard}, #{isCollege}, #{address}, #{degree}, #{graduationDate}, #{clazzId}, #{createTime}, #{updateTime})")
    void insert(Student student);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from student where id = #{id}")
    Student getById(Integer id);

    /**
     * 修改学生信息
     * @param student
     */
    void update(Student student);

    /**
     * 根据id删除学生
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 扣分
     * @param id
     * @param score
     */
    void updateViolation(Integer id, Integer score);

    /**
     * 获取学生数量
     * @return
     */
    @Select("select c.name cname, count(s.id) scount " +
            "from clazz c left join student s on c.id = s.clazz_id " +
            "group by c.name order by count(s.id) desc")
    List<Map<String, Object>> getStudentCount();

    /**
     * 获取学生学历
     * @return
     */
    @MapKey("name")
    List<Map<String, Object>> countStudentDegreeData();
}
