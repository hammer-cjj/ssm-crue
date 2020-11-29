package com.zsga.cf.ssm.dao;

import java.util.List;

import com.zsga.cf.ssm.entity.Dept;

public interface DeptDao {
	void insertDept(Dept dept);
	List<Dept> queryAllDept();
}
