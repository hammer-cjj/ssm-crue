package com.zsga.cf.ssm.test.excel;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.excel.EasyExcel;
import com.zsga.cf.ssm.entity.Emp;

public class ExcelTest {
	
	private final String PATH = "/Users/hammer-cjj/Desktop/";
	
	@Test
    public void simpleWrite() {
        // 写法1
        String fileName = PATH + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, Emp.class).sheet("员工列表").doWrite(data());

   
    }
	
	private List<Emp> data() {
        List<Emp> list = new ArrayList<Emp>();
        for (int i = 0; i < 10; i++) {
        	Emp data = new Emp();
            data.setEmpId(i+1);
            data.setEmpName("user" + i);
            data.setEmail("test" + i + "@email.com");
            data.setGender("M");
            list.add(data);
        }
        return list;
    }

}
