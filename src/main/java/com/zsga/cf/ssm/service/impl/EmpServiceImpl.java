package com.zsga.cf.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsga.cf.ssm.dao.EmpDao;
import com.zsga.cf.ssm.entity.Emp;
import com.zsga.cf.ssm.service.EmpService;

@Service
public class EmpServiceImpl implements EmpService {
	
	@Autowired
	private EmpDao empDao;
	

	@Override
	public List<Emp> getAllEmp(Emp empCondition) {
		return empDao.queryAllEmp(empCondition);
	}


	@Override
	public int saveEmp(Emp emp) {
		return empDao.insertEmp(emp);
	}


	@Override
	public boolean checkUserByName(String empName) {
		return empDao.queryUserByName(empName) == 0;
	}


	@Override
	public Emp getEmp(Integer empId) {
		return empDao.queryEmpById(empId);
	}


	@Override
	public void editEmp(Emp emp) {
		empDao.updateEmp(emp);
	}


	@Override
	public void removeEmp(Integer empId) {
		empDao.deleteEmpById(empId);
	}


	@Override
	public void removeEmpBatch(List<Integer> del_empIds) {
		empDao.deleteEmpBatch(del_empIds);
	}

}
