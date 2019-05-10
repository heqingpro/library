package com.ipanel.web.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ipanel.web.appUser.pageModel.AppUserModel;


public class ExcelUtil {

	public static class Excel {
		protected List<ExcelRecord> list = new LinkedList<ExcelRecord>();

		public List<ExcelRecord> getList() {
			return list;
		}

		public void setList(List<ExcelRecord> list) {
			this.list = list;
		}

	}

	public static class ExcelRecord {
		protected List<String> list = new ArrayList<String>();

		public List<String> getList() {
			return list;
		}

		public void setList(List<String> list) {
			this.list = list;
		}

	}

	public static Excel parseExcel(String fileType, InputStream in) {
		Excel result = new Excel();
		try {
			byte[] byteArray = FileUtil.getByteArrayFromInputStream(in);
			InputStream tempIn = new ByteArrayInputStream(byteArray);
			Workbook workbook = null;
			if ("xls".equals(fileType)) {
				workbook = new HSSFWorkbook(tempIn);
			} else if ("xlsx".equals(fileType)) {
				workbook = new XSSFWorkbook(tempIn);
			}

			if (workbook != null) {
				Sheet excelSheet = workbook.getSheetAt(0);
				// 获得工作簿的行数
				int rows = excelSheet.getLastRowNum();// getPhysicalNumberOfRows()返回的是不包含空行的行数，getLastRowNum()返回的是包含空行的行数
				for (int rowIndex = 1; rowIndex <= rows; rowIndex++) {
					Row row = excelSheet.getRow(rowIndex);
					ExcelRecord excelRecord = new ExcelRecord();
					int cells = row.getLastCellNum();// getPhysicalNumberOfCells()返回的是不包含空列的列数，getLastCellNum()返回的是包含空列的列数
					for (int cellIndex = 0; cellIndex < cells; cellIndex++) {
						if (row.getCell(cellIndex) != null) {
							row.getCell(cellIndex).setCellType(Cell.CELL_TYPE_STRING);
						}
						Cell oneCell = row.getCell(cellIndex);
						if (oneCell == null) {
							excelRecord.list.add("");
							continue;
						}
						String cellStringValue = getCellStringValue(oneCell);
						excelRecord.list.add(cellStringValue);
					}
					result.list.add(excelRecord);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<AppUserModel> excelToAppUserList(Excel excel) {
		List<AppUserModel> appUserModelList = new ArrayList<AppUserModel>();
		List<ExcelRecord> excelRecordList = excel.getList();
		if (excelRecordList != null && excelRecordList.size() > 0) {
			for (ExcelRecord excelRecord : excelRecordList) {
				List<String> eachRow = excelRecord.getList();
				AppUserModel appUserModel = new AppUserModel();
				if (eachRow.get(0) != null && !"".equals(eachRow.get(0))) {
					appUserModel.setCaId(eachRow.get(0));
				}
				/*if (eachRow.get(1) != null && !"".equals(eachRow.get(1))) {
					channelModel.setServiceId(eachRow.get(1));
				}

				if (eachRow.get(2) != null && !"".equals(eachRow.get(2))) {
					channelModel.setFrequency(Integer.parseInt(eachRow.get(2)));
				}
				if (eachRow.get(3) != null && !"".equals(eachRow.get(3))) {
					channelModel.setChannelNo(eachRow.get(3));
				}*/
				appUserModelList.add(appUserModel);
			}
		}
		return appUserModelList;
	}

	

	private static String getCellStringValue(Cell cell) {
		String result = null;
		Object o = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_ERROR: {
				break;
			}
			case Cell.CELL_TYPE_BOOLEAN: {
				o = cell.getBooleanCellValue();
				break;
			}
			case Cell.CELL_TYPE_FORMULA: {
			}
			case Cell.CELL_TYPE_NUMERIC: {
				o = Double.valueOf(cell.getNumericCellValue()).intValue();
				break;
			}
			case Cell.CELL_TYPE_BLANK: {
			}
			case Cell.CELL_TYPE_STRING:
				o = cell.getStringCellValue();
				break;
			}
		}

		if (o != null) {
			result = o.toString();
		}
		return result;
	}
	
	/**
	 * 将Log数据保存到excel表中
	 */
	@SuppressWarnings("resource")
	public static byte[] downloadLogToExcel(String msg) {
		
		try {
			JSONArray json = new JSONArray(msg);
			
			Workbook wb=new HSSFWorkbook();
			Sheet sheet=wb.createSheet("SPCMS_LOG");
			
			//设置表格样式
			CellStyle style=wb.createCellStyle();
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setWrapText(true);
			
			Row row=sheet.createRow(0);
			Cell cell=row.createCell(0);
			cell.setCellStyle(style);
			sheet.setColumnWidth(0, 3000);
			cell.setCellValue("编号");
			cell.setCellType(Cell.CELL_TYPE_STRING); 
			cell=row.createCell(1);
			cell.setCellStyle(style);
			sheet.setColumnWidth(1, 6000);
			cell.setCellValue("模块名称");
			cell.setCellType(Cell.CELL_TYPE_STRING); 
			cell=row.createCell(2);
			cell.setCellStyle(style);
			sheet.setColumnWidth(2, 4000);
			cell.setCellValue("操作用户");
			cell.setCellType(Cell.CELL_TYPE_STRING); 
			cell=row.createCell(3);
			cell.setCellStyle(style);
			sheet.setColumnWidth(3, 4000);
			cell.setCellValue("操作功能");
			cell.setCellType(Cell.CELL_TYPE_STRING); 
			cell=row.createCell(4);
			cell.setCellStyle(style);
			sheet.setColumnWidth(4, 25000);
			cell.setCellValue("操作描述");
			cell.setCellType(Cell.CELL_TYPE_STRING); 
			cell=row.createCell(5);
			cell.setCellStyle(style);
			sheet.setColumnWidth(5, 8000);
			cell.setCellValue("操作日期");
			cell.setCellType(Cell.CELL_TYPE_STRING); 
			
			for (int i = 0; i < json.length(); i++) {
				
				JSONObject jb = (JSONObject) json.get(i);
				
				row=sheet.createRow((i+1));
				cell=row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(jb.getString("id").toString());
				cell.setCellType(Cell.CELL_TYPE_STRING); 
				cell=row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue(jb.getString("moduleName"));
				cell.setCellType(Cell.CELL_TYPE_STRING); 
				cell=row.createCell(2);
				cell.setCellStyle(style);
				cell.setCellValue(jb.getString("userName"));
				cell.setCellType(Cell.CELL_TYPE_STRING); 
				cell=row.createCell(3);
				cell.setCellStyle(style);
				cell.setCellValue(jb.getString("operatingFunction"));
				cell.setCellType(Cell.CELL_TYPE_STRING); 
				cell=row.createCell(4);
				cell.setCellStyle(style);
				cell.setCellValue(jb.getString("operatingDesc"));
				cell.setCellType(Cell.CELL_TYPE_STRING); 
				cell=row.createCell(5);
				cell.setCellStyle(style);
				cell.setCellValue(jb.getString("operatingDate"));
				cell.setCellType(Cell.CELL_TYPE_STRING); 
			}
			
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			wb.write(baos);
			baos.flush();
			byte[] byteData=baos.toByteArray();
			return byteData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
