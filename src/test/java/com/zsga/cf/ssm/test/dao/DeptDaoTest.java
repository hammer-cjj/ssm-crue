package com.zsga.cf.ssm.test.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zsga.cf.ssm.dao.DeptDao;
import com.zsga.cf.ssm.dao.EmpDao;
import com.zsga.cf.ssm.entity.Dept;
import com.zsga.cf.ssm.test.BaseTest;

public class DeptDaoTest extends BaseTest {
	
	@Autowired
	private DeptDao deptDao;
	@Autowired
	private EmpDao empDao;
	
	@Test
	public void testInsertDept() {
		deptDao.insertDept(new Dept(null, "研发部"));
		deptDao.insertDept(new Dept(null, "测试部"));
	}

}
