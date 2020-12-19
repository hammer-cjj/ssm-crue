package com.zsga.cf.ssm.entity;

import com.alibaba.excel.annotation.ExcelProperty;
/**
 * Excel导出的bean
 * @author hammer-cjj
 *
 */
public class ExcelEmp {
	@ExcelProperty("编号")
    private Integer empId;

    @ExcelProperty("姓名")
    private String empName;

    @ExcelProperty("性别")
    private String gender;
    
    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("部门")
    private String deptName;

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
    
}
