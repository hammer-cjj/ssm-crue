package com.zsga.cf.ssm.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsga.cf.ssm.entity.Emp;
import com.zsga.cf.ssm.service.EmpService;

@Controller
public class EmpController {
	
	@Autowired
	private EmpService empService;

	/**
	 * 查询员工数据（PageHelper分页查询）
	 * @return
	 */
	@RequestMapping(value="/emps", method=RequestMethod.GET)
	public String getEmps(@RequestParam(value="pn", defaultValue="1")Integer pn, Model model) {
		PageHelper.startPage(pn, 10);
		List<Emp> empList = empService.getAllEmp(null); 
		// 连续显示五页
		PageInfo<Emp> page = new PageInfo<Emp>(empList, 5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
}
