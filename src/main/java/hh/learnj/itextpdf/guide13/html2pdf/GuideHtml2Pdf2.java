package hh.learnj.itextpdf.guide13.html2pdf;

import java.io.FileOutputStream;
import java.io.StringReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * Using iText HTMLWorker, you can produce PDF version of an HTML document. The
 * document must be simple. Many things like FORM elements or external images
 * are not supported.
 * 
 * @author Hunny.Hu
 *
 */
public class GuideHtml2Pdf2 {

	// itextpdf-5.4.1.jar http://sourceforge.net/projects/itext/files/iText/
	// xmlworker-5.4.1.jar http://sourceforge.net/projects/xmlworker/files/
	public static void main(String... args) {
		try {
			Document document = new Document(PageSize.LETTER);
			PdfWriter pdfWriter = PdfWriter.getInstance(document,
					new FileOutputStream("testpdf.pdf"));
			document.open();
			document.addAuthor("Real Gagnon");
			document.addCreator("Real's HowTo");
			document.addSubject("Thanks for your support");
			document.addCreationDate();
			document.addTitle("Please read this");

			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();

			String str = "<html><head></head><body>"
					+ "<a href='http://www.rgagnon.com/howto.html'><b>Real's HowTo</b></a>"
					+ "<h1>Show your support</h1>"
					+ "<p>It DOES cost a lot to produce this site - in ISP storage and transfer fees, "
					+ "in personal hardware and software costs to set up test environments, and above all,"
					+ "the huge amounts of time it takes for one person to design and write the actual content.</p>"
					+ "<p>If you feel that effort has been useful to you, perhaps you will consider giving something back?</p>"
					+ "<p>Donate using PayPal� to real@rgagnon.com.</p>"
					+ "<p>Contributions via PayPal are accepted in any amount</p>"
					+ "<P><br/><table border='1'><tr><td>Java HowTo</td></tr><tr>"
					+ "<td style='background-color:red;'>Javascript HowTo</td></tr>"
					+ "<tr><td>Powerbuilder HowTo</td></tr></table></p>"
					+ "</body></html>";
			worker.parseXHtml(pdfWriter, document, new StringReader(str));
			document.close();
			System.out.println("Done.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
