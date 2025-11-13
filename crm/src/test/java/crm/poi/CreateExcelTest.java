package crm.poi;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

public class CreateExcelTest {
	@SuppressWarnings("resource")
	@Test
	public void exceTest() {
		// 创建一个Excel
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建一个工作簿
		HSSFSheet sheet = wb.createSheet("学生列表");
		HSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("学号");
		headerRow.createCell(1).setCellValue("姓名");
		headerRow.createCell(2).setCellValue("性别");
		//加入10条学生信息，写到循环中
		for (int i = 1; i <= 10; i++) { 
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(i);
			row.createCell(1).setCellValue("小王"+i);
			row.createCell(2).setCellValue("男");
		}
		
		try {
			FileOutputStream os = new FileOutputStream("D:/student.xls");
			wb.write(os);
			//关闭资源
			wb.close();
			os.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}






	}
}
