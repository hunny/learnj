/**
 * 
 */
package hh.learnj.httpclient.usecase.qichacha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huzexiong
 *
 */
public class ExcelReader {

	private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);
	private static XSSFRow row = null;

	/**
	 * 
	 * @param path
	 *          文件全路径，例如：C:/abc/abc.xls
	 * @param handler
	 */
	public static void read(String path, Handler handler, int... sheets) {
		if (StringUtils.isBlank(path) || null == handler //
				|| null == sheets || sheets.length == 0) {
			logger.debug("参数不正确[{}],[{}]", path, handler);
			return;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(path));
		} catch (FileNotFoundException e2) {
			throw new IllegalArgumentException(e2);
		}
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(fis);
		} catch (IOException e2) {
			throw new IllegalArgumentException(e2);
		}
		for (int sheet : sheets) {
			XSSFSheet spreadsheet = workbook.getSheetAt(sheet);
			Iterator<Row> rowIterator = spreadsheet.iterator();
			int i = 1;
			List<String> header = new ArrayList<String>();
			while (rowIterator.hasNext()) {
				row = (XSSFRow) rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				if (i == 1) {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String value = readCell(cell);
						header.add(value);
					}
					handler.handle(i, header);
				} else {
					String[] values = new String[header.size()];
					for (int n = 0; n < values.length; n++) {
						Cell cell = row.getCell(n);
						String value = readCell(cell);
						values[n] = value;
						if (n >= values.length) {
							break;
						}
					}
					handler.handle(i, Arrays.asList(values));
				}
				i++;
			}
		}
		try {
			workbook.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param cell
	 * @return
	 */
	private static String readCell(Cell cell) {
		String value = null;
		if (null != cell) {
			try {
				value = cell.getStringCellValue();
			} catch (Exception e) {
				try {
					value = NumberToTextConverter.toText(cell.getNumericCellValue());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return value;
	}

	public interface Handler {
		void handle(int row, List<String> values);
	}

}
