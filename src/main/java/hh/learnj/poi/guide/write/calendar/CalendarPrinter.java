package hh.learnj.poi.guide.write.calendar;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class CalendarPrinter {
	
	private String[] weeks = new String[] { "一", "二", "三", "四", "五", "六", "日" };
	private OutputStream outputStream;
	private Map<String, Object> accessory = new HashMap<String, Object>();
	
	public void calcExcel() throws IOException {
		// 计算每一天的日历数据
		Calendar calendar = Calendar.getInstance();
		//边框颜色
		XSSFColor borderColor = getBorderColor();
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		for (int m = 0; m < 12; m++) {
			calendar.set(Calendar.MONTH, m);
			//开始日期
			calendar.set(Calendar.DATE, 1);
			int mMonth = calendar.get(Calendar.MONTH);
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DATE, -1);
			//结束日期
			Long eDate = DateUtils.truncate(calendar.getTime(), Calendar.DATE).getTime() / 1000;
			//开始日期
			calendar.set(Calendar.DATE, 1);
			Long sDate = DateUtils.truncate(calendar.getTime(), Calendar.DATE).getTime() / 1000;
			Map<Long, List<String>> leaveResult = queryResultByDate(sDate, eDate);
			if (null == leaveResult) {
				leaveResult = new HashMap<Long, List<String>>();
			}
			// Create a blank sheet
			XSSFSheet spreadsheet = workbook.createSheet((mMonth + 1) + "月");
			// 创建月份
			XSSFRow row = spreadsheet.createRow(1);
			XSSFCellStyle style6 = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			style6.setFont(font);
			style6.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);
			style6 = setCellBackgroundColor(style6, getMonthBackgroundColor());
			style6 = setBorderColor(style6, borderColor);
			for (int i = 0; i < weeks.length; i++) {
				XSSFCell cell = row.createCell((short) (i + 1));
				cell.setCellValue((mMonth + 1) + "月");
				cell.setCellStyle(style6);
			}
			// 创建周日历
			XSSFRow row2 = spreadsheet.createRow(2);
			spreadsheet.addMergedRegion(new CellRangeAddress(1, 1, 1, weeks.length));
			for (int i = 0; i < weeks.length; i++) {
				spreadsheet.autoSizeColumn(i + 1);
				// width = Truncate([{Number of Visible Characters} * {Maximum Digit
				// Width} + {5 pixel padding}]/{Maximum Digit Width}*256)/256
				spreadsheet.setColumnWidth(i + 1, 20 * 256);
				XSSFCell cell2B = row2.createCell(i + 1);
				cell2B.setCellValue(weeks[i]);
				// Style Cell 2B
				XSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);
				cellStyle = setCellBackgroundColor(cellStyle, getWeekBackgroundColor());
				cellStyle = setBorderColor(cellStyle, borderColor);
				cell2B.setCellStyle(cellStyle);
			}
	
			Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
			int dayRow = 0;// 当天所在的行
			while (true) {
				int month = calendar.get(Calendar.MONTH);
				if (month != mMonth) {// 不是当月的，结束
					if (2 == calendar.get(Calendar.DAY_OF_WEEK)) {//如果是周一的情况，那么行需要减一
						dayRow --;
					}
					calendar.add(Calendar.DATE, -1);
					break;
				}
				int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				int day = calendar.get(Calendar.DATE);
				Map<String, Object> meta = new HashMap<String, Object>();
				meta.put("day", day);
				meta.put("month", month);
				meta.put("date", DateUtils.truncate(calendar.getTime(), Calendar.DATE).getTime() / 1000);
				if (week == 0) {// 星期天
					week = 7;
				}
				meta.put("week", week);
				List<String> list = leaveResult.get(meta.get("date"));
				if (null != list) {
					if (list.size() <= 10) {
						meta.put("info", StringUtils.join(list, "、"));
					} else {
						meta.put("info", StringUtils.join(list.subList(0, 10), "、") + "。。。");
					}
				}
				map.put(week + "_" + dayRow, meta);
				if (week == 7) {
					dayRow++;
				}
				calendar.add(Calendar.DATE, 1);
			}
	
			for (int n = 0; n <= dayRow; n++) {
				// 创建每一天数据
				XSSFRow row3 = spreadsheet.createRow(3 + n);
				row3.setHeight((short) (8 * 256));
				for (int i = 0; i < weeks.length; i++) {
					XSSFCell cell2B = row3.createCell(i + 1);
					// Style Cell 2B
					XSSFCellStyle cellStyle = workbook.createCellStyle();
					cellStyle = workbook.createCellStyle();
					cellStyle.setWrapText(true);
					Map<String, Object> meta = map.get((i + 1) + "_" + n);
					if (null != meta) {
						String s = "";
						if (null != meta.get("info")) {
							s += "\n" + meta.get("info");
						}
						cell2B.setCellValue(meta.get("day") + s);
						cellStyle = setCellBackgroundColor(cellStyle, getDayBackgroundColor());
					}
					cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
					cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
					cellStyle = setBorderColor(cellStyle, borderColor);
					cell2B.setCellStyle(cellStyle);
				}
			}
		}
		workbook.write(getOutputStream());
		getOutputStream().flush();
		workbook.close();
	}
	
	public abstract Map<Long, List<String>> queryResultByDate(Long sDate, Long eDate);
	
	/**
	 * 设置单元格的背景颜色
	 * @param color
	 * @return
	 */
	protected XSSFCellStyle setCellBackgroundColor(XSSFCellStyle style, XSSFColor color) {
		style.setFillForegroundColor(color);
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		return style;
	}
	
	/**
	 * 设置单元格的边框颜色
	 * @param color
	 * @return
	 */
	protected XSSFCellStyle setBorderColor(XSSFCellStyle style, XSSFColor color) {
		style.setTopBorderColor(color);
		style.setBottomBorderColor(color);
		style.setRightBorderColor(color);
		style.setLeftBorderColor(color);
		style.setBorderTop((short) 1); // single line border
		style.setBorderBottom((short) 1); // single line border
		style.setBorderLeft((short) 1);
		style.setBorderRight((short) 1);
		return style;
	}
	
	/**
	 * 单元格边框的颜色
	 * @return
	 */
	protected XSSFColor getBorderColor() {
		byte[] bRGB = new byte[3];
		bRGB[0] = (byte) 242; // red
		bRGB[1] = (byte) 100; // green
		bRGB[2] = (byte) 50; // blue
		XSSFColor bColor = new XSSFColor(bRGB); // #f2dcdb
		return bColor;
	}
	
	/**
	 * 月份的背景颜色
	 * @return
	 */
	protected XSSFColor getMonthBackgroundColor() {
		byte[] rgb = new byte[3];
		rgb[0] = (byte) 242; // red
		rgb[1] = (byte) 220; // green
		rgb[2] = (byte) 219; // blue
		XSSFColor myColor = new XSSFColor(rgb); // #f2dcdb
		return myColor;
	}
	
	/**
	 * 周的背景颜色
	 * @return
	 */
	protected XSSFColor getWeekBackgroundColor() {
		byte[] aRGB = new byte[3];
		aRGB[0] = (byte) 242; // red
		aRGB[1] = (byte) 220; // green
		aRGB[2] = (byte) 100; // blue
		XSSFColor aColor = new XSSFColor(aRGB); // #f2dcdb
		return aColor;
	}
	
	/**
	 * 日的背景颜色
	 * @return
	 */
	protected XSSFColor getDayBackgroundColor() {
		byte[] aRGB = new byte[3];
		aRGB[0] = (byte) 176; // red
		aRGB[1] = (byte) 226; // green
		aRGB[2] = (byte) 255; // blue
		XSSFColor aColor = new XSSFColor(aRGB); // #f2dcdb
		return aColor;
	}
	
	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> clazz) {
		T t = (T)accessory.get(key);
		return t;
	}

	public <T> void put(String key, T t) {
		accessory.put(key, t);
	}

}
