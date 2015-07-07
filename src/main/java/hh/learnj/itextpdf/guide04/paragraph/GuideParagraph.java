package hh.learnj.itextpdf.guide04.paragraph;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * http://tutorials.jenkov.com/java-itext/paragraph.html
 * @author Hunny.Hu
 *
 */
public class GuideParagraph {

	public static void main(String[] args) {
		Document document = new Document();
	    try {
	      PdfWriter.getInstance(document, new FileOutputStream("Paragraph.pdf"));
	      document.open();
	      Paragraph paragraph = new Paragraph();
	      for(int i=0; i<10; i++){
	        Chunk chunk = new Chunk("This is a sentence which is long " + i + ". ");
	        paragraph.add(chunk);
	      }
	      document.add(paragraph);
	      document.close();
	    } catch (DocumentException e) {
	      e.printStackTrace();
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    }
	}

}
