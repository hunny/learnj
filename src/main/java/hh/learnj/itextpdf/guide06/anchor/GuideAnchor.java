package hh.learnj.itextpdf.guide06.anchor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

public class GuideAnchor {

	public static void main(String[] args) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Anchor.pdf"));
			document.open();
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Phrase("You can find the IText tutorial at "));
			Anchor anchor = new Anchor(
					"http://tutorials.jenkov.com/java-itext/index.html");
			anchor.setReference("http://tutorials.jenkov.com/java-itext/index.html");
			paragraph.add(anchor);
			document.add(paragraph);
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
