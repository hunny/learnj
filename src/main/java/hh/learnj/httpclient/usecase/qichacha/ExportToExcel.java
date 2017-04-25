/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * @author huzexiong
 *
 */
public class ExportToExcel {

	public static void main(String[] args) throws Exception {
		
		DB.handle(new DB.Hander() {
			@Override
			public void handle(Statement stmt) throws SQLException {
				ResultSet resultSet = stmt.executeQuery("select * from " + getTableName() + " where (CODE LIKE '%94060000%' OR CODE LIKE '%32041700%') and lastUpdated <= '2017-04-12 13:27:02' and mobile is not null ORDER BY ID DESC ");
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet spreadsheet = workbook.createSheet("qichacha");
				XSSFRow row = spreadsheet.createRow(1);
				XSSFCell cell;
				cell = row.createCell(1);
				cell.setCellValue("序号");
				cell = row.createCell(2);
				cell.setCellValue("企业名称");
				cell = row.createCell(3);
				cell.setCellValue("编码");
				cell = row.createCell(4);
				cell.setCellValue("地区");
				cell = row.createCell(5);
				cell.setCellValue("法人");
				cell = row.createCell(6);
				cell.setCellValue("联系方式");
				cell = row.createCell(7);
				cell.setCellValue("联系地址");
				cell = row.createCell(8);
				cell.setCellValue("销售代表");
				cell = row.createCell(9);
				cell.setCellValue("服务代表");
				cell = row.createCell(10);
				cell.setCellValue("客户状态");
				cell = row.createCell(11);
				cell.setCellValue("注册资本");
				int i = 2;
				while (resultSet.next()) {
					String mobile = resultSet.getString("mobile");
//					if (!mobile.matches("^1\\d{10}")) {
//						continue;
//					}
					row = spreadsheet.createRow(i);
					cell = row.createCell(1);
					cell.setCellValue("" + (i - 1));
					cell = row.createCell(2);
					cell.setCellValue(resultSet.getString("name"));
					cell = row.createCell(3);
					cell.setCellValue(resultSet.getString("code"));
					cell = row.createCell(4);
					cell.setCellValue(resultSet.getString("city"));
					cell = row.createCell(5);
					cell.setCellValue(resultSet.getString("businesser"));
					cell = row.createCell(6);
					cell.setCellValue(mobile);
					cell = row.createCell(7);
					cell.setCellValue(resultSet.getString("address"));
					cell = row.createCell(8);
					cell.setCellValue(resultSet.getString("saleman"));
					cell = row.createCell(9);
					cell.setCellValue(resultSet.getString("serviceman"));
					cell = row.createCell(10);
					cell.setCellValue(resultSet.getString("state"));
					cell = row.createCell(11);
					cell.setCellValue(resultSet.getString("registerAmount"));
					i++;
				}
				FileOutputStream out = null;
				String name = "d:/94060000_32041700.xlsx";
				try {
					out = new FileOutputStream(new File(name));
					workbook.write(out);
					out.close();
					workbook.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(String.format("excel %s written successfully", name));
			}
			@Override
			public String getTableName() {
				return "company";
			}
		});
	}

}
