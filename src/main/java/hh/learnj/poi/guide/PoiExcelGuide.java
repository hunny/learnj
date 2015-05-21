package hh.learnj.poi.guide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelGuide {

	public static void main(String[] args) {
		build();
	}
	
	public static void build() {
		try {
			writeToExcel();
			readFromExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeToExcel() throws Exception {
		// Create Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create file system using specific name
		FileOutputStream out = new FileOutputStream(new File(
				"d:/createworkbook.xlsx"));
		// write operation workbook using file out object
		workbook.write(out);
		out.close();
		workbook.close();
		System.out.println("createworkbook.xlsx written successfully");
	}

	public static void readFromExcel() throws Exception {
		File file = new File("d:/createworkbook.xlsx");
		FileInputStream fileInputStream = new FileInputStream(file);
		// Get the workbook instance for XLSX file
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		if (file.isFile() && file.exists()) {
			System.out.println("openworkbook.xlsx file open successfully.");
		} else {
			System.out.println("Error to open openworkbook.xlsx file.");
		}
		workbook.close();
	}

}
