package com.itheima.mapper;

import com.itheima.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    //插入日志数据
    @Insert("insert into operate_log (operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateEmpId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    public void insert(OperateLog log);

//    查询日志数据
//查询字段：log.* + e.name AS operate_emp_name
//
//作用：查出日志信息的同时把操作人名字也查出来
//
//结果类型是 OperateLog，它必须有对应字段接收 operate_emp_name
    @Select("SELECT log.*, e.name AS operate_emp_name " +
        "FROM operate_log log " +
        "LEFT JOIN emp e ON log.operate_emp_id = e.id " +
        "ORDER BY log.operate_time DESC")
    List<OperateLog> findByPage();


}
