package hh.learnj.itextpdf.guide09.image.position;

import java.io.FileOutputStream;
import java.net.URL;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class GuideImagePosition {

	public static void main(String[] args) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Image2.pdf"));
			document.open();
			String imageUrl = "http://jenkov.com/images/"
					+ "20081123-20081123-3E1W7902-small-portrait.jpg";
			Image image = Image.getInstance(new URL(imageUrl));
			image.setAbsolutePosition(500f, 650f);
			document.add(image);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
