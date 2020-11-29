package com.zsga.cf.ssm.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsga.cf.ssm.entity.Emp;
import com.zsga.cf.ssm.entity.Msg;
import com.zsga.cf.ssm.service.EmpService;

@Controller
public class EmpController {
	
	@Autowired
	private EmpService empService;
	
	/**
	 * 员工保存
	 * @param emp
	 * @return
	 */
	@RequestMapping(value="/emps", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@RequestBody Emp emp) {
		int result = empService.saveEmp(emp);
		if (result > 0) {
			return Msg.success();
		} else {
			return Msg.fail();
		}
		
	}
	
	@RequestMapping(value="/emps", method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn", defaultValue="1")Integer pn) {
		PageHelper.startPage(pn, 10);
		List<Emp> empList = empService.getAllEmp(null); 
		// 连续显示五页
		PageInfo<Emp> page = new PageInfo<Emp>(empList, 5);
		return Msg.success().add("pageInfo", page);
	}

	/**
	 * 查询员工数据（PageHelper分页查询）
	 * @return
	 */
	//@RequestMapping(value="/emps", method=RequestMethod.GET)
	public String getEmps(@RequestParam(value="pn", defaultValue="1")Integer pn, Model model) {
		PageHelper.startPage(pn, 10);
		List<Emp> empList = empService.getAllEmp(null); 
		// 连续显示五页
		PageInfo<Emp> page = new PageInfo<Emp>(empList, 5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
}
