/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author huzexiong
 *
 */
public class ExcelWriter {

	private static final Logger logger = LoggerFactory.getLogger(ExcelWriter.class);
	
	public static void wirte(String path, WriterHandler handler, String sheetName) {
		if (null == handler || null == handler.getHeaders() || handler.getHeaders().isEmpty()) {
			return;
		}
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet(sheetName);
		XSSFRow row = spreadsheet.createRow(0);
		XSSFCell cell = null;
		int headerSize = handler.getHeaders().size();
		for (int n = 0; n < headerSize; n++) {
			cell = row.createCell(n);
			cell.setCellValue(handler.getHeaders().get(n));
		}
		if (null != handler.getValues() && !handler.getValues().isEmpty()) {
			int valueSize = handler.getValues().size();
			for (int m = 0; m < valueSize; m++) {
				row = spreadsheet.createRow(m + 1);
				List<String> values = handler.getValues().get(m);
				for (int k = 0; k < values.size(); k++) {
					cell = row.createCell(k);
					cell.setCellValue(values.get(k));
				}
			}
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(path));
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info(String.format("excel %s written successfully", path));
	}
	
	public interface WriterHandler {
		
		List<String> getHeaders();
		
		List<List<String>> getValues();
		
	}
	
}
