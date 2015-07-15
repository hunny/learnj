package hh.learnj.itextpdf.guide13.html2pdf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class GuideHtml2Pdf {

	public static void main(String[] args) throws Exception {
		// step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("pdf.pdf"));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                getInputStream()); 
        //step 5
         document.close();
        System.out.println( "PDF Created!" );
	}
	
	public static InputStream getInputStream() throws Exception {
		return new FileInputStream("index.html");
	}

}
