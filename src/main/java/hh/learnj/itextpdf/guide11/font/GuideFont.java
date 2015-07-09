package hh.learnj.itextpdf.guide11.font;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

public class GuideFont {

	public static void main(String[] args) {
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream("Font.pdf"));

			Font font1 = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD);
			Font font2 = new Font(Font.FontFamily.COURIER, 18, Font.ITALIC
					| Font.UNDERLINE);
			Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 27);

			document.open();

			document.add(new Chunk("This is sentence 1. ", font1));
			document.add(new Phrase("This is sentence 2. ", font2));
			document.add(new Paragraph("This is sentence 3. ", font3));

			document.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
