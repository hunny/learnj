package hh.learnj.itextpdf.guide07.list;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.pdf.PdfWriter;

public class GuideList {

	public static void main(String[] args) {
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream("List.pdf"));

			document.open();

			List orderedList = new List(List.ORDERED);
			orderedList.add(new ListItem("Item 1"));
			orderedList.add(new ListItem("Item 2"));
			orderedList.add(new ListItem("Item 3"));

			document.add(orderedList);

			List unorderedList = new List(List.UNORDERED);
			unorderedList.add(new ListItem("Item 1"));
			unorderedList.add(new ListItem("Item 2"));
			unorderedList.add(new ListItem("Item 3"));

			document.add(unorderedList);

			document.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
