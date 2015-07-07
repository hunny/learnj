package hh.learnj.itextpdf.guide05.chaptersection;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * http://tutorials.jenkov.com/java-itext/chapter-section.html
 * @author Hunny.Hu
 *
 */
public class GuideChapterSection {

	public static void main(String[] args) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					"ChapterSection.pdf"));
			document.open();
			Paragraph paragraph = new Paragraph();
			paragraph.add(new Phrase("This is a chapter."));
			Chapter chapter = new Chapter(paragraph, 1);
			Section section1 = chapter.addSection("This is section 1", 2);
			Section section2 = chapter.addSection("This is section 2", 2);
			document.add(chapter);
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
