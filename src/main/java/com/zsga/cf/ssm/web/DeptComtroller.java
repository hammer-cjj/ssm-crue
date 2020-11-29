package com.zsga.cf.ssm.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zsga.cf.ssm.entity.Dept;
import com.zsga.cf.ssm.entity.Msg;
import com.zsga.cf.ssm.service.DeptService;

@Controller
public class DeptComtroller {
	@Autowired
	private DeptService deptService;
	
	@RequestMapping(value="/depts", method=RequestMethod.GET)
	@ResponseBody
	public Msg getDepts() {
		List<Dept> deptList =deptService.getAllDept();
		return Msg.success().add("depts", deptList);
	}
}
