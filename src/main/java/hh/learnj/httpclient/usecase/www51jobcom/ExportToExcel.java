package hh.learnj.httpclient.usecase.www51jobcom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportToExcel {

	public static void main(String[] args) throws Exception {
		
		DB.handle(null, new DB.Hander() {
			@Override
			public void handle(List<Map<String, String>> values, Statement stmt) throws SQLException {
				ResultSet resultSet = stmt.executeQuery("select * from " + getTableName());
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet spreadsheet = workbook.createSheet("51job");
				XSSFRow row = spreadsheet.createRow(1);
				XSSFCell cell;
				cell = row.createCell(1);
				cell.setCellValue("序号");
				cell = row.createCell(2);
				cell.setCellValue("企业名称");
				cell = row.createCell(3);
				cell.setCellValue("51job地址");
				cell = row.createCell(4);
				cell.setCellValue("企业官网地址");
				cell = row.createCell(5);
				cell.setCellValue("企业地址");
				cell = row.createCell(6);
				cell.setCellValue("企业详情");
				int i = 2;
				while (resultSet.next()) {
					row = spreadsheet.createRow(i);
					cell = row.createCell(1);
					cell.setCellValue("" + (i - 1));
					cell = row.createCell(2);
					cell.setCellValue(resultSet.getString("name"));
					cell = row.createCell(3);
					cell.setCellValue(resultSet.getString("url51"));
					cell = row.createCell(4);
					cell.setCellValue(resultSet.getString("url"));
					cell = row.createCell(5);
					cell.setCellValue(resultSet.getString("address"));
					cell = row.createCell(6);
					cell.setCellValue(resultSet.getString("details"));
					i++;
				}
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(new File(
							"d:/exceldatabase.xlsx"));
					workbook.write(out);
					out.close();
					workbook.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("exceldatabase.xlsx written successfully");
			}
			@Override
			public String getTableName() {
				return "company";
			}
		});
	}

}
