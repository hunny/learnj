package hh.learnj.poi.guide.write;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class HSSFCellBorderGuide {

	public static void main(String[] args) throws Exception {
		/* Create Workbook and Worksheet */
		HSSFWorkbook my_workbook = new HSSFWorkbook();
		HSSFSheet my_sheet = my_workbook.createSheet("Cell Borders");
		/* Get access to HSSFCellStyle */
		HSSFCellStyle my_style = my_workbook.createCellStyle();
		/* We are now ready to set borders for this style */
		/* Draw a thin left border */
		my_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		/* Add medium right border */
		my_style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		/* Add dashed top border */
		my_style.setBorderTop(HSSFCellStyle.BORDER_DASHED);
		/* Add dotted bottom border */
		my_style.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);

		/* Let us create another style now */
		HSSFCellStyle my_style_2 = my_workbook.createCellStyle();

		/* Draw a thick left border */
		my_style_2.setBorderLeft(HSSFCellStyle.BORDER_THICK);
		/* Draw double lined right border */
		my_style_2.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
		/* Add dotted top border - hairy */
		my_style_2.setBorderTop(HSSFCellStyle.BORDER_HAIR);
		/* Add medium dashed bottom border */
		my_style_2.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM_DASHED);

		/* One more style to illusrtate more border patterns */

		HSSFCellStyle my_style_3 = my_workbook.createCellStyle();

		/* border dash dotted */
		my_style_3.setBorderLeft(HSSFCellStyle.BORDER_DASH_DOT);
		/* border medium dash dot */
		my_style_3.setBorderRight(HSSFCellStyle.BORDER_MEDIUM_DASH_DOT);
		/* border dash dot dot */
		my_style_3.setBorderTop(HSSFCellStyle.BORDER_DASH_DOT_DOT);
		/* border medium dash dot dot */
		my_style_3.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM_DASH_DOT_DOT);

		/* Attach border styles to cell */
		Row row = my_sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Add Border Example - 1 ");
		cell.setCellStyle(my_style);

		row = my_sheet.createRow(1);
		row = my_sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("Add Border Example - 2 ");
		cell.setCellStyle(my_style_2);

		row = my_sheet.createRow(3);
		row = my_sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellValue("Add Border Example - 3 ");
		cell.setCellStyle(my_style_3);

		/* Write changes to the workbook */
		FileOutputStream out = new FileOutputStream(new File(
				"d:/cell_border_Example.xls"));
		my_workbook.write(out);
		my_workbook.close();
		out.close();

	}

}
