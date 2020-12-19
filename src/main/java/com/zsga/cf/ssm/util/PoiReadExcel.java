package com.zsga.cf.ssm.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * POI解析Excel
 * @author hammer-cjj
 *
 */
public class PoiReadExcel {
	public static void main(String[] args) {
		//创建文件
		File file = new File("/Users/hammer-cjj/Desktop/poi_test.xls");
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(file));
			//HSSFSheet sheet = workbook.getSheet("Sheet0");
			HSSFSheet sheet = workbook.getSheetAt(0);
			int firstRowNum = 0;
			//获取sheet中的最后一行
			int lastRowNum = sheet.getLastRowNum();
			for (int i=firstRowNum; i<=lastRowNum; i++) {
				HSSFRow row = sheet.getRow(i);
				//获取该行最后单元格列号
				int lastCellNum = row.getLastCellNum();
				for (int j=0; j<lastCellNum; j++) {
					HSSFCell cell = row.getCell(j);
					String v = cell.getStringCellValue();
					System.out.print(v + " ");
				}
				System.out.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
