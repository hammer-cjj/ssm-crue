package com.zsga.cf.ssm.service;

import java.util.List;

import com.zsga.cf.ssm.entity.Emp;

public interface EmpService {
	List<Emp> getAllEmp(Emp empCondition);
}
