package hh.learnj.itextpdf.guide09.image.scaling;

import java.io.FileOutputStream;
import java.net.URL;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * http://tutorials.jenkov.com/java-itext/image.html
 * 
 * scaleAbsolute()
 * scaleAbsoluteWidth()
 * scaleAbsoluteHeight()
 * scalePercentage()
 * scaleToFit()
 * 
 * @author Hunny.Hu
 *
 */
public class GuideImageScaling {

	public static void main(String[] args) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Image3.pdf"));
			document.open();
			String imageUrl = "http://jenkov.com/images/"
					+ "20081123-20081123-3E1W7902-small-portrait.jpg";
			Image image = Image.getInstance(new URL(imageUrl));
			image.scaleAbsolute(150f, 150f);
			document.add(image);

			Image image2 = Image.getInstance(new URL(imageUrl));
			image2.scalePercent(300f);
			document.add(image2);

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
