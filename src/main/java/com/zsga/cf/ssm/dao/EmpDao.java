package com.zsga.cf.ssm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zsga.cf.ssm.entity.Emp;

public interface EmpDao {
	void insertEmp(Emp emp);
	/**
	 * 查询所有员工
	 * @param empCondition
	 * @return
	 */
	List<Emp> queryAllEmp(@Param("empCondition")Emp empCondition);
}
