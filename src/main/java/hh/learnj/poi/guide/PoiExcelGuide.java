package hh.learnj.poi.guide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelGuide {

	public static void main(String[] args) {
		build();
	}
	
	public static void build() {
		try {
			writeCalendarToExcel();
//			writeToExcel();
//			readFromExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeCalendarToExcel() throws Exception {
		// Create Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("new sheet");
		//创建Row
		XSSFRow row = sheet.createRow(1);
		XSSFCell cell = row.createCell((short) 1);
		XSSFCellStyle style6 = workbook.createCellStyle();
		
		byte[] rgb = new byte[3];
		rgb[0] = (byte) 242; // red
		rgb[1] = (byte) 220; // green
		rgb[2] = (byte) 219; // blue
		XSSFColor myColor = new XSSFColor(rgb); // #f2dcdb
		style6.setFillForegroundColor(myColor);
		
		style6.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);
		style6.setBorderBottom((short) 1); // single line border
		cell.setCellValue("一月份");
		cell.setCellStyle(style6);
		sheet.addMergedRegion(new CellRangeAddress(
		        1, //first row (0-based)
		        1, //last row  (0-based)
		        1, //first column (0-based)
		        7  //last column  (0-based)
		));
		
		XSSFRow row1 = sheet.createRow(2);
        XSSFCell cell2B = row1.createCell(1);
        cell2B.setCellValue(new XSSFRichTextString("Sample String"));
		// Style Cell 2B
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(myColor);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        
        byte[] bRGB = new byte[3];
        bRGB[0] = (byte) 242; // red
        bRGB[1] = (byte) 0; // green
        bRGB[2] = (byte) 100; // blue
		XSSFColor bColor = new XSSFColor(bRGB); // #f2dcdb
        cellStyle.setTopBorderColor(bColor);
        cellStyle.setBorderTop((short) 1); // single line border
//        cellStyle.setBorderBottom((short) 1); // single line border
        cell2B.setCellStyle(cellStyle);
		
		// Create file system using specific name
		FileOutputStream out = new FileOutputStream(new File(
				"d:/createworkbook.xlsx"));
		// write operation workbook using file out object
		workbook.write(out);
		out.close();
		workbook.close();
		System.out.println("createworkbook.xlsx written successfully");
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
