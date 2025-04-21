package com.itheima.mapper;

import com.itheima.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName:EmpExprMapper.xml
 * Package:com.itheima.mapper
 * Description:
 *
 * @date:2025-04-03 10:38 p.m.
 * @author:Qss
 */

@Mapper
public interface EmpExprMapper {
    /**
     * 批量保存员工的工作经历
     * @param exprList
     */
    void insertBatch(List<EmpExpr> exprList);

    /**
     * 根据员工id批量删除工作经历
     * @param empIds
     */
    void deleteByEmpIds(List<Integer> empIds);
}
