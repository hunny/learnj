package hh.learnj.itextpdf.guide09.image;

import java.io.FileOutputStream;
import java.net.URL;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * http://tutorials.jenkov.com/java-itext/image.html
 * @author Hunny.Hu
 *
 */
public class GuideImage {

	public static void main(String[] args) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Image.pdf"));
			document.open();
			Image image1 = Image.getInstance(GuideImage.class
					.getResource("/itextpdf_image_watermark.png"));
			document.add(image1);
			String imageUrl = "http://jenkov.com/images/"
					+ "20081123-20081123-3E1W7902-small-portrait.jpg";
			Image image2 = Image.getInstance(new URL(imageUrl));
			document.add(image2);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
