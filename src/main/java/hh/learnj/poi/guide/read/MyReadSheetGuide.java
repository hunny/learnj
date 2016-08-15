package hh.learnj.poi.guide.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
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
public class MyReadSheetGuide {
	static XSSFRow row;
	private static final Map<String, String> codeMap = new HashMap<String, String>();
	static {
		codeMap.put("爱康国宾", "001");
		codeMap.put("美年大健康", "002");
	}
	
	private static Map<String, String> cityMap = new HashMap<String, String>();

	public static void main(String[] args) throws Exception {
		
		final Map<String, String> map = new HashMap<String, String>();
		map.putAll(codeMap);
		getMapRowAtSheet(0, new MyRowCellValue() {
			@Override
			public String invoke(int row, Map<Integer, String> result) {
				if (0 == row) {
					return null;
				}
				if (null == result || null == result.get(0)) {
					return null;
				}
				StringBuffer buffer = new StringBuffer();
				buffer.append("INSERT INTO shopnc_pe_organization (org_code, org_name, contacts, contact_info) VALUES (");
				for (int i = 0; i < 3; i++) {
					String key = result.get(i);
					if (i == 0) {
						String code = map.get(key);
						if (null == code) {
							System.err.println(key + "没有定义或者已经使用");
							return null;
						}
						map.remove(key);
						buffer.append("'");
						buffer.append(code);
						buffer.append("',");
					}
					if (null != key) {
						key = key.replaceAll("'", "''");
						buffer.append("'");
						buffer.append(key);
						buffer.append("'");
					} else {
						buffer.append(key);
					}
					buffer.append(",");
				}
				buffer.append(");\r\n");
				return buffer.toString().replaceFirst(",\\);$", ");");
			}
		});
		getMapRowAtSheet(1, new MyRowCellValue() {
			@Override
			public String invoke(int row, Map<Integer, String> result) {
				if (0 == row || 1 == row) {
					return null;
				}
				if (null == result || null == result.get(0)) {
					return null;
				}
				StringBuffer buffer = new StringBuffer();
				buffer.append("INSERT INTO shopnc_pe_branch (org_code, name, city_id, address, contact_info, worktime, offday) VALUES (");
				String offday = null;
				StringBuffer citySql = new StringBuffer();
				citySql.append("INSERT INTO shopnc_pe_city (name, en_prefix) VALUES(");
				for (int i = 0; i < 13; i++) {
					String key = result.get(i);
					if (i == 0) {
						key = codeMap.get(key);
					} else if (i == 2) {//取城市
						if (null != key) {
							String keys [] = key.split("/");
							if (keys.length > 1 && !"".equals(keys[1])) {
								if (null == cityMap.get(keys[1])) {
									keys[0] = keys[0].toUpperCase();
									citySql.append(handleColumn(keys[1]));
									citySql.append(handleColumn(keys[0]));
									citySql.append(");\r\n");
									cityMap.put(keys[1], keys[0]);
								} else {
									citySql = null;
								}
								key = "(select id from shopnc_pe_city where name = '" + keys[1] + "' limit 1)";
							} else {
								key = null;
							}
						} else {
							key = null;
						}
					} else if (i >= 6) {
						if (null != key) {
							if (null == offday) {
								offday = "";
							}
							offday += (i - 5) + ",";
						}
						if (i < 12) {
							continue;
						}
						if (null != offday) {
							key = offday.replaceFirst(",$", "");
						}
					}
					buffer.append(handleColumn(key));
				}
				buffer.append(");\r\n");
				String sSql = buffer.toString().replaceFirst(",\\);$", ");");
				sSql = (null != citySql) ? (citySql.toString().replaceFirst(",\\);$", ");") + sSql) : sSql;
				return sSql;
			}
			
			private String handleColumn(String key) {
				StringBuffer buffer = new StringBuffer();
				if (null != key) {
					boolean quote = true;
					if (key.startsWith("(")) {
						quote = false;
					}
					if (quote) {
						key = key.replaceAll("'", "''");
					}
					key = key.replaceAll("\r", "");
					key = key.replaceAll("\n", "");
					key = key.replaceAll("\r\n", "");
					buffer.append(quote ? "'" : "");
					buffer.append(key);
					buffer.append(quote ? "'" : "");
				} else {
					buffer.append(key);
				}
				buffer.append(",");
				return buffer.toString();
			}
		});
	}
	
	
	public static void getMapRowAtSheet(Integer index, MyRowCellValue rowCellValue) throws Exception {
		FileInputStream fis = new FileInputStream(new File(
				"C:/Users/Hunny.hu/Desktop/体检机构及分院初始数据.xlsx"));
		File file = new File("D:/php/workspace/db/V5/CL-1205_INSERT.sql");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet spreadsheet = workbook.getSheetAt(index);
		Iterator<Row> rowIterator = spreadsheet.iterator();
		DataFormatter fmt = new DataFormatter();
		FileWriter write = new FileWriter(file, (index != 0));
		while (rowIterator.hasNext()) {
			row = (XSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Map<Integer, String> result = new HashMap<Integer, String>();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String value = null;
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
					value = fmt.formatCellValue(cell);
					break;
				case Cell.CELL_TYPE_STRING:
					value = cell.getStringCellValue();
					break;
				}
				result.put(cell.getColumnIndex(), value);
				System.out.print(value + "|");
			}
			System.out.println();
			String sql = rowCellValue.invoke(row.getRowNum(), result);
			if (null != sql) {
				write.write(sql);
			}
		}
		write.close();
		workbook.close();
		fis.close();
	}
	
}
