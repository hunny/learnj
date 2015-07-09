package hh.learnj.itextpdf.guide09.image.rotating;

import java.io.FileOutputStream;
import java.net.URL;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * setRotationDegrees()
 * setRotation()
 * @author Hunny.Hu
 *
 */
public class GuideImageRotating {

	public static void main(String[] args) {
		Document document = new Document();
	    try {
	      PdfWriter.getInstance(document,
	            new FileOutputStream("Image4.pdf"));
	      document.open();
	      
	      String imageUrl = "http://jenkov.com/images/" +
	              "20081123-20081123-3E1W7902-small-portrait.jpg";

	      Image image = Image.getInstance(new URL(imageUrl));
	      image.setRotationDegrees(45f);
	      document.add(image);

	      document.close();
	    } catch(Exception e){
	      e.printStackTrace();
	    }
	}

}
