package com.zsga.cf.ssm.test.dao;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zsga.cf.ssm.dao.EmpDao;
import com.zsga.cf.ssm.entity.Dept;
import com.zsga.cf.ssm.entity.Emp;
import com.zsga.cf.ssm.test.BaseTest;

public class EmpDaoTest extends BaseTest {
	@Autowired
	private EmpDao empDao;
	@Autowired
	private SqlSession sqlSessionBatch;
	
	@Test
	public void testInsertEmp() {
		Emp emp = new Emp();
		emp.setDept(new Dept(1, "研发部"));
		emp.setEmail("quadcopter@email.com");
		emp.setEmpName("quadcopter");
		emp.setGender("M");
		empDao.insertEmp(emp);
	}
	
	@Test
	public void testInsertBatch() {
		EmpDao empDao = sqlSessionBatch.getMapper(EmpDao.class);
		for (int i=0; i<500; i++) {
			String uuid = UUID.randomUUID().toString().substring(0, 5) + i;
			Emp emp = new Emp();
			emp.setDept(new Dept(1, "研发部"));
			emp.setEmail(uuid + "@email.com");
			emp.setEmpName(uuid);
			emp.setGender("M");
			empDao.insertEmp(emp);
		}
	}
	
	@Test
	public void testQueryAllEmp() {
		Emp emp = new Emp();
		emp.setDept(new Dept(2, "测试部"));
		List<Emp> empList = empDao.queryAllEmp(emp);
		Assert.assertEquals(empList.size(), 1);
	}
	
	@Test
	public void testQueryUserByName() {
		String empName = "cf";
		int count = empDao.queryUserByName(empName);
		Assert.assertEquals(1, count);
	}
}
