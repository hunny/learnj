package hh.learnj.testj.poi.guide.word;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.junit.Test;

public class ReadWordByWordExtractorTest {

	@Test
	public void testReadByExtractor() throws Exception {
		InputStream is = new FileInputStream("/Users/hunnyhu/Documents/委托书.doc");
		WordExtractor extractor = new WordExtractor(is);
		// 输出word文档所有的文本
		System.out.println("====================> 输出word文档所有的文本");
		System.out.println(extractor.getText());
		System.out.println("====================> 输出word文档TextFromPieces");
		System.out.println(extractor.getTextFromPieces());
		System.out.println("====================> 输出word文档页眉的内容");
		// 输出页眉的内容
		System.out.println("页眉：" + extractor.getHeaderText());
		System.out.println("====================> 输出word文档页脚的内容");
		// 输出页脚的内容
		System.out.println("页脚：" + extractor.getFooterText());
		System.out.println("====================> 输出当前word文档的元数据信息，包括作者、文档的修改时间等。");
		// 输出当前word文档的元数据信息，包括作者、文档的修改时间等。
		System.out.println(extractor.getMetadataTextExtractor().getText());
		// 获取各个段落的文本
		System.out.println("====================> 获取各个段落的文本");
		String paraTexts[] = extractor.getParagraphText();
		for (int i = 0; i < paraTexts.length; i++) {
			System.out.println("Paragraph " + (i + 1) + " : " + paraTexts[i]);
		}
		System.out.println("====================> 输出当前word的一些信息");
		// 输出当前word的一些信息
		printInfo(extractor.getSummaryInformation());
		System.out.println("====================> 输出当前word的一些信息");
		// 输出当前word的一些信息
		this.printInfo(extractor.getDocSummaryInformation());
		extractor.close();
		this.closeStream(is);
	}

	/**
	 * 输出SummaryInfomation
	 * 
	 * @param info
	 */
	private void printInfo(SummaryInformation info) {
		// 作者
		System.out.println(info.getAuthor());
		// 字符统计
		System.out.println(info.getCharCount());
		// 页数
		System.out.println(info.getPageCount());
		// 标题
		System.out.println(info.getTitle());
		// 主题
		System.out.println(info.getSubject());
	}

	/**
	 * 输出DocumentSummaryInfomation
	 * 
	 * @param info
	 */
	private void printInfo(DocumentSummaryInformation info) {
		// 分类
		System.out.println(info.getCategory());
		// 公司
		System.out.println(info.getCompany());
	}

	/**
	 * 关闭输入流
	 * 
	 * @param is
	 */
	private void closeStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
