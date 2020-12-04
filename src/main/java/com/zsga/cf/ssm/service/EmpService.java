package com.zsga.cf.ssm.service;

import java.util.List;

import com.zsga.cf.ssm.entity.Emp;

public interface EmpService {
	List<Emp> getAllEmp(Emp empCondition);
	int saveEmp(Emp emp);
	boolean checkUserByName(String empName);
	Emp getEmp(Integer empId);
	void editEmp(Emp emp);
	void removeEmp(Integer empId);
	void removeEmpBatch(List<Integer> del_empIds);
}
