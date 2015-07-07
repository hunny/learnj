package hh.learnj.itextpdf.guide02.chunk;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * http://tutorials.jenkov.com/java-itext/chunk.html
 * @author Hunny.Hu
 *
 */
public class GuideChunk {

	public static void main(String[] args) {
		Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("Chunk.pdf"));
            document.open();
            document.add(new Chunk("This is sentence 1. "));
            document.add(new Chunk("This is sentence 2. "));
            document.add(new Chunk("This is sentence 3. "));
            document.add(new Chunk("This is sentence 4. "));
            document.add(new Chunk("This is sentence 5. "));
            document.add(new Chunk("This is sentence 6. "));
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}

}
