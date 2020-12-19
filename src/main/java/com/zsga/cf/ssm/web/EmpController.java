package com.zsga.cf.ssm.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsga.cf.ssm.entity.Emp;
import com.zsga.cf.ssm.entity.ExcelEmp;
import com.zsga.cf.ssm.entity.Msg;
import com.zsga.cf.ssm.service.EmpService;

@Controller
public class EmpController {
	
	@Autowired
	private EmpService empService;
	
	@GetMapping("/excelexport")
	public void excelExport(HttpServletResponse response) throws IOException{
		try {
			response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("员工表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            
            //获取数据
            List<Emp> empList = empService.getAllEmp(null); 
            //转换成需要导出的内容
            List<ExcelEmp> excels = new ArrayList<>();
            
            for (int i=0; i<empList.size(); i++) {
            	Emp e = empList.get(i);
            	ExcelEmp ee = new ExcelEmp();
            	ee.setEmpId(e.getEmpId());
            	ee.setEmpName(e.getEmpName());
            	ee.setGender(e.getGender().equals("M")?"男":"女");
            	ee.setDeptName(e.getDept().getDeptName());
            	ee.setEmail(e.getEmail());
            	excels.add(ee);
            }
            
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), ExcelEmp.class).autoCloseStream(Boolean.FALSE).sheet("员工列表")
                .doWrite(excels);
		} catch(Exception e) {
			// 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().println(objectMapper.writeValueAsString(map));
		}
	}
	
	
	/**
	 * 删除员工
	 * 批量删除：1-2-3
	 * 单个删除：1
	 * @param empId
	 * @return
	 */
	@RequestMapping(value="/emps/{empIds}", method=RequestMethod.DELETE)
	@ResponseBody
	public Msg removeEmpById(@PathVariable("empIds")String empIds) {
		
		if (empIds.contains("-")) { //批量删除
			List<Integer> del_empIds = new ArrayList<>();
			String[] str_empIds = empIds.split("-");
			for (String s : str_empIds) {
				del_empIds.add(Integer.parseInt(s));
			}
			empService.removeEmpBatch(del_empIds);
		} else { //单个删除
			Integer empId = Integer.parseInt(empIds);
			empService.removeEmp(empId);
		}
		
		return Msg.success();
	}
	
	/**
	 * 更新员工信息
	 * @param emp
	 * @return
	 */
	@RequestMapping(value="/emps/{empId}", method=RequestMethod.PUT)
	@ResponseBody
	public Msg saveEmp(@RequestBody Emp emp) {
		System.out.println(emp);
		empService.editEmp(emp);
		return Msg.success();
	}
		
	/**
	 * 根据员工ID查询员工信息
	 * @param empId
	 * @return
	 */
	@RequestMapping(value="/emps/{empId}", method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("empId")Integer empId) {
		Emp emp = empService.getEmp(empId);
		return Msg.success().add("emp", emp);
	}
	
	/**
	 * 检查用户名是否已存在
	 * @param empName
	 * @return
	 */
	@RequestMapping("/checkuser")
	@ResponseBody
	public Msg checkUser(@RequestParam("empName")String empName) {
		//先判断用户名是否合法
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
		if (!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "用户名必须是2-5位中文或者是6-16位英文和数字的组合");
		}
		//数据库校验
		boolean b = empService.checkUserByName(empName);
		if (b) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "用户名已存在");
		}
	}
	
	/**
	 * 员工保存
	 * @param emp
	 * @return
	 */
	@RequestMapping(value="/emps", method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid @RequestBody Emp emp, BindingResult result) {
		if (result.hasErrors()) {
			//校验失败
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors =  result.getFieldErrors();
			for (FieldError fieldError : errors) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		} else {
			int code = empService.saveEmp(emp);
			if (code > 0) {
				return Msg.success();
			} else {
				return Msg.fail();
			}
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
	@RequestMapping(value="/emps2", method=RequestMethod.GET)
	public String getEmps(@RequestParam(value="pn", defaultValue="1")Integer pn, Model model) {
		PageHelper.startPage(pn, 10);
		List<Emp> empList = empService.getAllEmp(null); 
		// 连续显示五页
		PageInfo<Emp> page = new PageInfo<Emp>(empList, 5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
	
}
