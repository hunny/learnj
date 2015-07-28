package hh.learnj.poi.guide.write.calendar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

public class ExcelPrinter {

	public static void main(String[] args) throws Exception {
		CalendarPrinter printer = new CalendarPrinter() {
			@Override
			public Map<Long, List<String>> queryResultByDate(Long sDate,
					Long eDate) {
				return null;
			}
		};
		OutputStream out = new FileOutputStream(new File("D:/abc.xlsx"));
		printer.setOutputStream(out);
		printer.calcExcel();
		
		Date date = new Date();
		System.out.println(date);
		System.out.println(DateFormat.getDateTimeInstance().format(date));
		System.out.println(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date));
		System.out.println(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(date));
		System.out.println(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(date));
		System.out.println(DateUtils.truncate(date, Calendar.DATE));
	}

}
