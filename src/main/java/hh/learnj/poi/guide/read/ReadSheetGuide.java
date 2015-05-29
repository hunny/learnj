package hh.learnj.poi.guide.read;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Components of Apache POI Apache POI contains classes and methods to work on
 * all OLE2 Compound documents of MS Office. The list of components of this API
 * is given below. POIFS (Poor Obfuscation Implementation File System) : This
 * component is the basic factor of all other POI elements. It is used to read
 * different files explicitly.
 * 
 * HSSF (Horrible Spreadsheet Format) : It is used to read and write xls format
 * of MS-Excel files.
 * 
 * XSSF (XML Spreadsheet Format) : It is used for xlsx file format of MS-Excel.
 * 
 * HPSF (Horrible Property Set Format) : It is used to extract property sets of
 * the MS-Office files.
 * 
 * HWPF (Horrible Word Processor Format) : It is used to read and write doc
 * extension files of MS-Word.
 * 
 * XWPF (XML Word Processor Format) : It is used to read and write docx
 * extension files of MS-Word.
 * 
 * HSLF (Horrible Slide Layout Format) : It is used for read, create, and edit
 * PowerPoint presentations.
 * 
 * HDGF (Horrible DiaGram Format) : It contains classes and methods for MS-Visio
 * binary files.
 * 
 * HPBF (Horrible PuBlisher Format) : It is used to read and write MS-Publisher
 * files.
 * 
 * @author Hunny.Hu
 *
 */
public class ReadSheetGuide {

	static XSSFRow row;

	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream(new File(
				"d:/WriteSheetTest.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet spreadsheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = spreadsheet.iterator();
		while (rowIterator.hasNext()) {
			row = (XSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue() + " \t\t ");
					break;
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue() + " \t\t ");
					break;
				}
			}
			System.out.println();
		}
		workbook.close();
		fis.close();
	}

}
