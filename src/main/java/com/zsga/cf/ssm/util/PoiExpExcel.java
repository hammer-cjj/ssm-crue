package com.zsga.cf.ssm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * POI创建Excel
 * @author hammer-cjj
 *
 */
public class PoiExpExcel {
	public static void main(String[] args) {
		String[] title = {"id", "name", "sex"};
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = workbook.createSheet();
		//创建第一行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for (int i=0; i<title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		//追加数据
		for (int i=1; i<=10; i++) {
			HSSFRow nextrow = sheet.createRow(i);
			HSSFCell cell2 = nextrow.createCell(0);
			cell2.setCellValue("a" + i);
			cell2 = nextrow.createCell(1);
			cell2.setCellValue("user" + i);
			cell2 = nextrow.createCell(2);
			cell2.setCellValue("男");
		}
		
		//创建文件
		File file = new File("/Users/hammer-cjj/Desktop/poi_test.xls");
		try {
			file.createNewFile();
			FileOutputStream fos = FileUtils.openOutputStream(file);
			workbook.write(fos);
			fos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
