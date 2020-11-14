package com.zsga.cf.ssm.test.web;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.zsga.cf.ssm.entity.Emp;
import com.zsga.cf.ssm.test.BaseTest;

@WebAppConfiguration
@ContextConfiguration({"classpath:spring/spring-web.xml", 
"classpath:mybatis-config.xml"})
public class EmpControllerTest extends BaseTest {
	@Autowired
	WebApplicationContext context;
	MockMvc mockMvc;
	
	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testGetEmps() throws Exception {
		// 模拟请求，返回数据。
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5")).andReturn();
		MockHttpServletRequest request = mvcResult.getRequest();
		PageInfo pi = (PageInfo) request.getAttribute("pageInfo");
		System.out.println("当前页码：" + pi.getPageNum());
		System.out.println("总页码：" + pi.getPages());
		System.out.println("总记录数：" + pi.getTotal());
		int[] nums = pi.getNavigatepageNums();
		System.out.println("连续显示的页数：" + Arrays.toString(nums));
		List<Emp> list = pi.getList();
		for (Emp e : list) {
			System.out.println("Id: " + e.getEmpId() + " name: " + e.getEmpName());
		}
	}
}
