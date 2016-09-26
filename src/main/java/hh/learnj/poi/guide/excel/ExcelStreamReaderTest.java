/**
 * 
 */
package hh.learnj.poi.guide.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.monitorjbl.xlsx.StreamingReader;

/**
 * 
 * @author huzexiong
 *
 */
public class ExcelStreamReaderTest {

	public static void main(String[] args) {
		try {
			long start = System.currentTimeMillis();
			testNative2003();
			System.out.println("2003 cost:" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			long start = System.currentTimeMillis();
			testNative2007();
			System.out.println("2007 cost:" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			long start = System.currentTimeMillis();
			testStreamReader();
			System.out.println("reader cost:" + (System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testNative2003() throws Exception {
	// HSSFWorkbook, File
	  NPOIFSFileSystem fs = new NPOIFSFileSystem(new File("C:/Users/huzexiong/Desktop/Excel 97-2003 工作表.xls"));
	  HSSFWorkbook workbook2003 = new HSSFWorkbook(fs.getRoot(), true);
	  handler(workbook2003.getSheetAt(0));
	  workbook2003.close();
	  fs.close();
	}
	public static void testNative2007() throws Exception {
	  // XSSFWorkbook, File
	  OPCPackage pkg = OPCPackage.open(new File("C:/Users/huzexiong/Desktop/Excel文件测试.xlsx"));
	  XSSFWorkbook workbook2007 = new XSSFWorkbook(pkg);
	  handler(workbook2007.getSheetAt(0));
	  workbook2007.close();
	  pkg.close();
	}

	public static void testStreamReader() throws Exception {
		InputStream is = new FileInputStream(new File("C:/Users/huzexiong/Desktop/Excel文件测试.xlsx"));
		Workbook workbook = StreamingReader.builder().rowCacheSize(2).bufferSize(4096).open(is);
		Sheet sheet = workbook.getSheetAt(0);
		handler(sheet);
		workbook.close();
		is.close();
	}
	
	public static void handler(Sheet sheet) {
		System.out.println(sheet.getSheetName());
		for (Row r : sheet) {
			for (Cell c : r) {
				try {
					System.out.print(c.getStringCellValue());
				} catch (Exception e) {
					System.out.print(c.getNumericCellValue());
				}
				System.out.print("|");
			}
			System.out.println();
		}
	}

	public static boolean isExcel(InputStream i) throws IOException {
		return (POIFSFileSystem.hasPOIFSHeader(i) || POIXMLDocument.hasOOXMLHeader(i));
	}

}
