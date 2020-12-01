package com.zsga.cf.ssm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zsga.cf.ssm.entity.Emp;

public interface EmpDao {
	/**
	 * 查询所有员工
	 * @param empCondition
	 * @return
	 */
	List<Emp> queryAllEmp(@Param("empCondition")Emp empCondition);
	
	/**
	 * 添加员工
	 * @param emp
	 * @return
	 */
	int insertEmp(Emp emp);

	/**
	 * 根据名字查询员工
	 * @param empName
	 * @return
	 */
	int queryUserByName(String empName);
}
