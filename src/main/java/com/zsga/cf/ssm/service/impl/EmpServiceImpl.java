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

}
