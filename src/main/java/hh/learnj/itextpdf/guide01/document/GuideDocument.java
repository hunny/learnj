package hh.learnj.itextpdf.guide01.document;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

/**
 * http://tutorials.jenkov.com/java-itext/document.html
 * @author Hunny.Hu
 */
public class GuideDocument {

	public static void main(String[] args) {
		Document document = new Document();
        try {
            document.open();
            document.add(new Paragraph("A Hello World PDF document."));
            document.close(); // no need to close PDFwriter?
        } catch (DocumentException e) {
            e.printStackTrace();
        }
	}

}
