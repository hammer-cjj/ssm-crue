package com.zsga.cf.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zsga.cf.ssm.dao.DeptDao;
import com.zsga.cf.ssm.entity.Dept;
import com.zsga.cf.ssm.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptDao deptDao;

	@Override
	public List<Dept> getAllDept() {
		return deptDao.queryAllDept();
	}

}
