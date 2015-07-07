package hh.learnj.itextpdf.guide08.table;

import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * http://tutorials.jenkov.com/java-itext/table.html
 * @author Hunny.Hu
 */
public class GuideTable {

	public static void main(String[] args) {
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					"HelloWorld-Table.pdf"));

			document.open();
			PdfPTable table = new PdfPTable(3); // 3 columns.
			PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			//Table Width
			table.setWidthPercentage(100);
			
			//Spacing Before and After Table
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10f);
			
			//Column Widths
			float[] columnWidths = {2f, 1f, 1f};
			table.setWidths(columnWidths);
			
			PdfPCell cell4 = new PdfPCell(new Paragraph("Cell 4"));
			//Column Span
			cell4.setColspan(3);
			cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);

			cell4.setVerticalAlignment(Element.ALIGN_TOP);
			cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell4.setVerticalAlignment(Element.ALIGN_BOTTOM);
			table.addCell(cell4);
			
			PdfPCell textModeCell = new PdfPCell(new Paragraph("Text Mode"));
			table.addCell(textModeCell);
			PdfPCell compositeModeCell = new PdfPCell();
			compositeModeCell.addElement(new Paragraph("Composite Mode"));
			table.addCell(compositeModeCell);

			table.addCell(new Paragraph("Text Mode"));
			
			PdfPCell cell5 = new PdfPCell(new Paragraph("Cell Rotation"));
			cell5.setColspan(3);
			cell5.setRotation(90);
			
			//Cell Leading
			cell5.setLeading(15f, 0f);
			cell5.setLeading(0f, 1.5f);
			
			//Cell Padding
			cell5.setPadding(5);
			cell5.setPaddingLeft(8);
			cell5.setPaddingRight(8);
			cell5.setPaddingTop(8);
			cell5.setPaddingBottom(8);
			
			//Cell Borders and Colors
			cell5.setBackgroundColor(BaseColor.YELLOW);   //sets BG color to yellow.
			cell5.setBorder(Rectangle.NO_BORDER);   // removes border
			cell5.setBorderWidth      (3f);         // sets border width to 3 units
			cell5.setBorderWidthLeft  (1f);
			cell5.setBorderWidthRight (1f);
			cell5.setBorderWidthTop   (1f);
			cell5.setBorderWidthBottom(1f);
			cell5.setBorderColor      (BaseColor.BLUE);  // sets blue border
			cell5.setBorderColorLeft  (BaseColor.GREEN);
			cell5.setBorderColorRight (BaseColor.GREEN);
			cell5.setBorderColorTop   (BaseColor.GREEN);
			cell5.setBorderColorBottom(BaseColor.GREEN);
//			cell5.setUserBorderPadding(true);
			
			table.addCell(cell5);
			
			document.add(table);

			document.close();
		} catch (Exception e) {

		}
	}

}
