package hh.learnj.itextpdf.guide12.modify;

import hh.learnj.itextpdf.guide09.image.GuideImage;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * IText can modify existing PDF files in many different ways. Here I'll just
 * cover one of the most used modifications - stamping an existing PDF with text
 * or images. Get the book "IText in Action" to get the full story on
 * manipulating existing PDF documents.
 * 
 * If you already have a finished PDF, and just want to add a header, footer or
 * watermark to it, IText provides the com.itextpdf.pdf.PdfStamper class.
 * 
 * First you read the existing document using a PdfReader, then modify it using
 * the PdfStamper.
 * 
 * Adding Content to the PDF Document
 * 
 * To add content to the document you need to access to a PdfContentByte from
 * the PdfStamper. You can add content either above or below the existing
 * content in the PDF document. Here is how you obtain the PdfContentByte from
 * the PdfStamper.
 * 
 * PdfContentByte underContent = pdfStamper.getUnderContent(1);
 * 
 * PdfContentByte overContent = pdfStamper.getOverContent(1);
 * 
 * The number passed as parameter is the page number of the page to get the
 * under or over content for.
 * 
 * The PdfContentByte object has methods for adding all kinds of content to a
 * PDF including text, graphics, images etc.
 * 
 * @author Hunny.Hu
 */
public class GuidePdfModified {

	public static void main(String[] args) {
		try {
			PdfReader pdfReader = new PdfReader("HelloWorld.pdf");

			PdfStamper pdfStamper = new PdfStamper(pdfReader,
					new FileOutputStream("HelloWorld-Stamped.pdf"));

			Image image = Image.getInstance(GuideImage.class
					.getResource("/itextpdf_image_watermark.png"));

			for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
				PdfContentByte content = pdfStamper.getUnderContent(i);
				image.setAbsolutePosition(100f, 700f);
				content.addImage(image);
			}

			pdfStamper.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
