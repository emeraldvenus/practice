package com.kayak.util.excel;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelUtil {
	private static Logger logger = Logger.getLogger(ExcelUtil.class);
	/*
	 * 获取单元格内容
	 */
	public static Object getObjectCellValue(HSSFCell cell){
		Object value = null;
		if(cell != null){
			int type = cell.getCellType();
			switch(type){
				//单元格类型为数字
				case HSSFCell.CELL_TYPE_NUMERIC:
					DecimalFormat dfs = new DecimalFormat("0");//取一位整数
					double numericCellValue = cell.getNumericCellValue();
					value = dfs.format(numericCellValue);
					break;
				//单元格类型为字符
				case HSSFCell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
			}
		}
		return value;
	}
	/*
	 * 读取Excel
	 */
	public static ArrayList readExcel(String filePath){
		try
		{
			POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(filePath));//得到Excel文档对象
			HSSFWorkbook workbook = new HSSFWorkbook(fs);//得到Excel工作簿对象
			HSSFSheet sheet = workbook.getSheetAt(0);//得到工作表对象
			int firstRow = sheet.getFirstRowNum();//第一行的行号
			int lastRow = sheet.getLastRowNum();//最后一行的行号
			//循环遍历工作表每一行
			ArrayList sheetList = new ArrayList<>();
			for(int i = firstRow;i<=lastRow;i++){
				HSSFRow row = sheet.getRow(i);
				if(row == null){
					continue;
				}
				short firstCellNum = row.getFirstCellNum();//获取第一列的列号
				short lastCellNum = row.getLastCellNum();//获取最后一列的列号
				ArrayList rowList = new ArrayList<>();
				//循环遍历该行的每一个单元格
				for(int j = firstCellNum;j<lastCellNum;j++){
					HSSFCell cell = row.getCell(j);
					rowList.add(getObjectCellValue(cell));
				}
				sheetList.add(rowList);
			}
			return sheetList;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("", e);
		}
		finally{
			
		}
		return null;
	}
	public static void main(String[] args) {
		String filePath = "F:\\Work\\kayak\\其他\\excelPractice.xls";
		ArrayList readExcel = readExcel(filePath);
		for(int i = 0;i<readExcel.size();i++){
			System.out.println(readExcel.get(i));
		}
	}
}
