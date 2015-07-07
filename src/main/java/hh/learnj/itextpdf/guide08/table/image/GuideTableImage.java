package hh.learnj.itextpdf.guide08.table.image;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GuideTableImage {

	public static void main(String[] args) {
		Document document = new Document();

        try {
            PdfWriter.getInstance(document,
                new FileOutputStream("Table3.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(2); // 3 columns.

            Image image = Image.getInstance("jakob-jenkov.jpg");
            PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
            PdfPCell cell2 = new PdfPCell(image, false);

            table.addCell(cell1);
            table.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(image, true);
            PdfPCell cell4 = new PdfPCell(new Paragraph("Cell 4"));

            table.addCell(cell3);
            table.addCell(cell4);

            document.add(table);

            document.close();
        } catch(Exception e){

        }
	}

}
