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

	/**
	 * 根据员工ID查询员工信息
	 * @param empId
	 * @return
	 */
	Emp queryEmpById(Integer empId);

	/**
	 * 更新员工信息
	 * @param emp
	 */
	void updateEmp(Emp emp);

	/**
	 * 删除员工
	 * @param empId
	 */
	void deleteEmpById(Integer empId);

	/*
	 * 批量删除员工
	 */
	void deleteEmpBatch(List<Integer> del_empIds);
}
