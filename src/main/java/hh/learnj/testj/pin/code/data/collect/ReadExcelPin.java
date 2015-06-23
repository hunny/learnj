package hh.learnj.testj.pin.code.data.collect;

import hh.learnj.testj.pin.code.sqlite.JdbcSqliteUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcelPin {
	
	private static Logger logger = Logger.getLogger(ReadExcelPin.class);
	
	static HSSFRow row;

	public static void main(String[] args) throws Exception {
//		readPin4();
		read1500();
	}
	
	public static void readPin4() throws Exception {
		FileInputStream fis = new FileInputStream(new File(
				"/Users/hunnyhu/Downloads/MAC对应PIN码表(MAC对应PIN前4位).xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet spreadsheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = spreadsheet.iterator();
		JdbcSqliteUtil util = new JdbcSqliteUtil();
		while (rowIterator.hasNext()) {
			row = (HSSFRow) rowIterator.next();
			String BSSID = getCellValue(row.getCell(0));
			BSSID = handleBSSID(BSSID);
			String PIN = getCellValue(row.getCell(1));
			String ESSID = getCellValue(row.getCell(2));
			List<Map<String, Object>> list = util.query("select * from reaver_wash where bssid = '" + BSSID + "'");
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("BSSID", BSSID);
			values.put("PIN", PIN);
			values.put("ESSID", ESSID);
			if (null == list || list.isEmpty()) {
				logger.debug(values);
				util.saveToWashReaver(values);
			} else {
				for (Map<String, Object> m : list) {
					String mPIN = (String)m.get("PIN");
					if (PIN.length() > mPIN.length()) {
						Map<String, Object> wheres = new HashMap<String, Object>();
						wheres.put("ID", m.get("ID"));
						util.updateToWashReaver(values, wheres);
						logger.debug("[+] [-]" + BSSID + " already existed." + mPIN + " will be updated by " + PIN);
					} else {
						logger.debug("[+] [-]" + BSSID + " already existed." + PIN);
					}
				}
			}
			System.out.println();
		}
		workbook.close();
		fis.close();
	}
	
	/**
	 * /Users/hunnyhu/Downloads/1500个无线路由器pin码表.xls
	 * 
	 * @throws Exception
	 */
	public static void read1500() throws Exception {
		FileInputStream fis = new FileInputStream(new File(
				"/Users/hunnyhu/Downloads/1500个无线路由器pin码表.xls"));
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet spreadsheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = spreadsheet.iterator();
		JdbcSqliteUtil util = new JdbcSqliteUtil();
		boolean first = true;
		while (rowIterator.hasNext()) {
			row = (HSSFRow) rowIterator.next();
			if (first) {
				first = false;
				continue;
			}
			String BSSID = getCellValue(row.getCell(3));
			BSSID = handleBSSID(BSSID);
			String PIN = getCellValue(row.getCell(4));
			String ESSID = getCellValue(row.getCell(2));
			String COLTD = getCellValue(row.getCell(1));
			List<Map<String, Object>> list = util.query("select * from reaver_wash where bssid = '" + BSSID + "'");
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("BSSID", BSSID);
			values.put("PIN", PIN);
			values.put("ESSID", ESSID);
			values.put("COLTD", COLTD);
			if (null == list || list.isEmpty()) {
				logger.debug(values);
				util.saveToWashReaver(values);
			} else {
				for (Map<String, Object> m : list) {
					String mPIN = (String)m.get("PIN");
					if (PIN.length() > mPIN.length()) {
						Map<String, Object> wheres = new HashMap<String, Object>();
						wheres.put("ID", m.get("ID"));
						util.updateToWashReaver(values, wheres);
						logger.debug("[+] [-]" + BSSID + " already existed." + mPIN + " will be updated by " + PIN);
					} else {
						logger.debug("[+] [-]" + BSSID + " already existed." + PIN);
					}
				}
			}
			System.out.println();
		}
		workbook.close();
		fis.close();
	}
	
	public static String handleBSSID(String BSSID) {
		BSSID = BSSID.replaceAll("\\s", "");
		BSSID = BSSID.replaceAll("-", ":");
		BSSID = BSSID.replaceAll("：", ":");
		BSSID = BSSID.toUpperCase();
		BSSID = BSSID.replaceAll("\\*", "");
		BSSID = BSSID.replaceAll("#", "");
		BSSID = BSSID.replaceAll("X", "");
		if (BSSID.indexOf(":") < 0) {
			String tmp = "";
			int len = BSSID.length();
			for (int i = 0; i < len / 2; i++) {
				tmp += BSSID.substring(i * 2, (i + 1) * 2) + ":";
			}
			BSSID = tmp.replaceFirst(":$", "");
		}
		BSSID = BSSID.replaceFirst(":$", "");
		return BSSID;
	}
	
	public static String getCellValue(HSSFCell cell) {
		if (null == cell) {
			return null;
		}
		String value = null;
		if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
			value = String.valueOf(new Double(cell.getNumericCellValue()).intValue());
		} else {
			value = cell.getStringCellValue();
		}
		return value;
	}

}
