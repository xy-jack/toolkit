package com.yd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.billionsfinance.collectsm.common.utils.PropertiesUtils;



/**
 * 提供2003,2007的excle导入
 * @author zhao.hong
 */
public class ExcelUtils {  
	
	
	public ExcelUtils(){
		
	}

	/**  
	 * 对外提供读取excel 的方法  
	 * @author hongzhao
	 * @param file 读取的文件
	 * @param fileName 需要导入的文件名
	 * @param path 上传的服务器路径
	 * @return List<List<Object>>
	 * @throws IOException 
	 * */    
	public static List<List<Object>> readExcel(File file, String fileName, String path) throws IOException{
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		String extension = fileName.lastIndexOf(".")==-1?"":fileName.substring(fileName.lastIndexOf(".")+1);    
		String filePath = path +File.separator + fileName;
		FileInputStream fis = null;  
		FileOutputStream fos = null;  
		try{
			fis = new FileInputStream(file);
			fos = new FileOutputStream(filePath);  //服务器上创建
			

			byte[] buffer = new byte[1024];  
			int len = 0;  
			while ((len = fis.read(buffer)) != -1) {  
				fos.write(buffer, 0, len);  
			}  
//			System.out.println("文件上传成功");   
		} catch (Exception e) {  
//			System.out.println("文件上传失败");   
			e.printStackTrace();  
		} finally {  
			fos.close();
			fis.close();
		} 
		
		if("xls".equals(extension)){    
			return read2003Excel(new File(filePath));    
		}
		else{
			return read2007Excel(new File(filePath));    
		}
	}   
	
	/**  
	 * 读取excle 
	 * @author hongzhao
	 * @param file 读取的文件
	 * @return List<List<Object>>
	 * @throws IOException 
	 * */ 
	private static List<List<Object>> read2003Excel(File file) throws IOException {    
		List<List<Object>> list = new ArrayList<List<Object>>();    
		
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径     
		
		HSSFWorkbook xwb = new HSSFWorkbook(new FileInputStream(file));
		// 读取第一章表格内容     
		HSSFSheet sheet = xwb.getSheetAt(0);
		Object value = null;    
		HSSFRow row = null;    
		HSSFCell cell = null;    
		DecimalFormat df = new DecimalFormat("0"); // 格式化 number String 字符     
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化日期字符串     
		DecimalFormat nf = new DecimalFormat("0"); // 格式化数字     
		
		PropertiesUtils utils = new PropertiesUtils();
		//获取导入数据最大数量+1来验证导入数据是否超过最大允许值
		int size = utils.getConfigInt("excel_max_size");
		if(sheet.getFirstRowNum() - 1 < size){
			size = sheet.getFirstRowNum();
		}
		for (int i = sheet.getFirstRowNum() + 1; i <= size; i++) {    
			row = sheet.getRow(i);    
			if (row == null) {    
				continue;    
			}    
			List<Object> array = new ArrayList<Object>();  
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {    
				cell = row.getCell(j);    
				if (cell == null) {    
					continue;    
				}    
				switch (cell.getCellType()) {    
				case XSSFCell.CELL_TYPE_STRING:    
					//   System.out.println(i+"行"+j+" 列 is String type");     
					value = cell.getStringCellValue();    
					break;    
				case XSSFCell.CELL_TYPE_NUMERIC:    
					//   System.out.println(i+"行"+j+" 列 is Number type ; DateFormt:"+cell.getCellStyle().getDataFormatString());     
					if("@".equals(cell.getCellStyle().getDataFormatString())){    
						value = df.format(cell.getNumericCellValue());    
					} else if("General".equals(cell.getCellStyle().getDataFormatString())){    
						value = nf.format(cell.getNumericCellValue());    
					}else{    
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));    
					}    
					break;    
				case XSSFCell.CELL_TYPE_BOOLEAN:    
					//   System.out.println(i+"行"+j+" 列 is Boolean type");     
					value = cell.getBooleanCellValue();    
					break;    
				case XSSFCell.CELL_TYPE_BLANK:    
					//      System.out.println(i+"行"+j+" 列 is Blank type");     
					value = "";    
					break;    
				default:    
					//   System.out.println(i+"行"+j+" 列 is default type");     
					value = cell.toString();    
				}    
				if (value == null || "".equals(value)) {    
					continue;    
				}    
				array.add(value);    
			}
			if(array.size() > 1){
				list.add(array);    
			}
		}    
		file.delete();
		return list;    
	} 
	
	/**  
	 * 读取excle 
	 * @author hongzhao
	 * @param file 读取的文件
	 * @return List<List<Object>>
	 * @throws IOException 
	 * */ 
	private static List<List<Object>> read2007Excel(File file) throws IOException {    
		List<List<Object>> list = new ArrayList<List<Object>>();    
		
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径     
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));   
		// 读取第一章表格内容     
		XSSFSheet sheet = xwb.getSheetAt(0);    
		Object value = null;    
		XSSFRow row = null;    
		XSSFCell cell = null;    
		DecimalFormat df = new DecimalFormat("0"); // 格式化 number String 字符     
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 格式化日期字符串     
		DecimalFormat nf = new DecimalFormat("0"); // 格式化数字     
		
		PropertiesUtils utils = new PropertiesUtils();
		//获取导入数据最大数量-1来验证导入数据是否超过最大允许值
		int size = utils.getConfigInt("excel_max_size");
		if(sheet.getFirstRowNum() - 1 <= size){
			size = sheet.getLastRowNum();
		}
		for (int i = sheet.getFirstRowNum() + 1; i <= size; i++) {    
			row = sheet.getRow(i);    
			if (row == null) {    
				continue;    
			}    
			List<Object> array = new ArrayList<Object>();  
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {    
				cell = row.getCell(j);    
				if (cell == null) {    
					continue;    
				}    
				switch (cell.getCellType()) {    
				case XSSFCell.CELL_TYPE_STRING:    
					value = cell.getStringCellValue();    
					break;    
				case XSSFCell.CELL_TYPE_NUMERIC:    
					if("@".equals(cell.getCellStyle().getDataFormatString())){    
						value = df.format(cell.getNumericCellValue());    
					} else if("General".equals(cell.getCellStyle().getDataFormatString())){    
						value = nf.format(cell.getNumericCellValue());    
					}else{    
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));    
					}    
					break;    
				case XSSFCell.CELL_TYPE_BOOLEAN:    
					value = cell.getBooleanCellValue();    
					break;    
				case XSSFCell.CELL_TYPE_BLANK:    
					value = "";    
					break;    
				default:    
					value = cell.toString();    
				}    
//				if (value == null || "".equals(value)) {    
//					continue;    
//				}    
				array.add(value);    
			}    
			if(array.size() > 1){
				list.add(array);    
			}
		}   
		file.delete();
		return list;    
	} 
	/**
	 * 对外提供导出Excel
	 * @param Object[] titles 表的标题
	 * @param List content 表的内容 (sql查询所得的list)
	 * @throws FileNotFoundException 
	 */
	public static SXSSFWorkbook genExcel(Object[] titles, List content) throws FileNotFoundException{
		SXSSFWorkbook book=null;
		try{
			book =new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);  
			SXSSFSheet sheet = book.createSheet();
			SXSSFRow row = sheet.createRow(0);
			int cellNum =0;
			//设置表的标题
			for(Object obj :titles){
				row.createCell(cellNum).setCellValue(obj.toString());
				cellNum++;
			}
			//设置表体的内容
			int cellSize = content.size();
			Object[] objCell =null;//存放内容的行数组
			for (int i = 0; i < cellSize; i++) {
				
				objCell = (Object[]) content.get(i);
				row = sheet.createRow(i+1);
				for(int j = 0; j < objCell.length; j++){
					row.createCell(j).setCellValue(objCell[j] == null ? "":objCell[j].toString());
				}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return book;
	}
//	/**
//	 * 
//	 * @param titles 表的标题
//	 * @param list 含有Object的list 
//	 * @param columns[] 需要的列名，即Object的属性
//	 * @return
//	 * @throws FileNotFoundException
//	 */
//	public static SXSSFWorkbook genExcelByColumns(String[] titles, List list, String[] columns) throws FileNotFoundException{
//		SXSSFWorkbook book=null;
//		if(titles.equals(null)||list.equals(null)||columns.equals(null)){
//			return null;
//		}
//		try{
//			book =new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
//			SXSSFSheet sheet = book.createSheet();
//			SXSSFRow row = sheet.createRow(0);
//			int cellNum =0;
//			//设置表的标题
//			for(Object obj :titles){
//				row.createCell(cellNum).setCellValue(obj.toString());
//				cellNum++;
//			}
//			//设置表体的内容
//			int cellSize = list.size();
//			Object objCell =null;//存放内容的行数组
//			for (int i = 0; i < cellSize; i++) {
//				objCell =  (BaseEntity) list.get(i);
//				row = sheet.createRow(i+1);
//				for(int j = 0; j < columns.length; j++){
//					row.createCell(j).setCellValue(objCell.getClass().getDeclaredField(columns[j]).toString());
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return book;
//	}
}  
