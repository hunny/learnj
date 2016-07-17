package hh.learnj.testj.xml;

import hh.learnj.database.jdbc.sqlite.JdbcSqliteSms;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XmlServiceTest {

	protected String DIR = "/Users/hunnyhu/Desktop";

	public Map<String, SmsInfo> readFromXml(String xml) throws Exception {
		Map<String, SmsInfo> map = new HashMap<String, SmsInfo>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		if (null == xml) {
			xml = "/smsbackup.xml";
		}
		File file = new File(DIR + xml);
		Document document = builder.parse(file);
		Element root = document.getDocumentElement();
		NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			NodeList childDetails = child.getChildNodes();
			SmsInfo smsInfo = new SmsInfo();
			for (int n = 0; n < childDetails.getLength(); n++) {
				Node details = childDetails.item(n);
				if (details instanceof Element) {
					Element element = (Element)details;
					Text textNode = (Text)element.getFirstChild();
					String nodeName = element.getTagName();
					String setter = "set" + nodeName.substring(0, 1).toUpperCase()
							+ nodeName.substring(1, nodeName.length());
					Method method = smsInfo.getClass().getMethod(setter,
							String.class);
					method.invoke(smsInfo, textNode.getData());
				}
			}
			map.put(smsInfo.getKey(), smsInfo);
		}
		return map;
	}
	
	public void createToXml(List<SmsInfo> list) throws Exception {
		File file = new File(DIR + "/test.xml");
		
		String xml10pattern = "[^"
				+ "\u0009\r\n"
				+ "\u0020-\uD7FF"
				+ "\uE000-\uFFFD"
				+ "\ud800\udc00-\udbff\udfff"
				+ "]";
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		Element root = document.createElement("smsinfos");
		
		if (null != list) {

			for (SmsInfo smsInfo : list) {
				Element smsinfoElement = document.createElement("smsinfo");
				
				Element phoneElement = document.createElement("phone");
				Text phone = document.createTextNode("phone");
				phone.setData(smsInfo.getPhone());
				phoneElement.appendChild(phone);
				smsinfoElement.appendChild(phoneElement);
				
				Element dateElement = document.createElement("date");
				Text date = document.createTextNode("date");
				date.setData(smsInfo.getDate());
				dateElement.appendChild(date);
				smsinfoElement.appendChild(dateElement);
				
				Element typeElement = document.createElement("type");
				Text type = document.createTextNode("type");
				type.setData(smsInfo.getType());
				typeElement.appendChild(type);
				smsinfoElement.appendChild(typeElement);
				
				Element bodyElement = document.createElement("body");
				Text body = document.createTextNode("body");
				body.setData(smsInfo.getBody().replaceAll(xml10pattern, ""));
				bodyElement.appendChild(body);
				smsinfoElement.appendChild(bodyElement);
				
				root.appendChild(smsinfoElement);
			}
		}
		
		document.appendChild(root);
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
//		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, 
//				"http://www.w3.org/TR/2000/CR-SVG-20000802/DTD/svg-200000802.dtd");
//		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//W3C//DTD SVG 20000802//EN");
		Source source = new DOMSource(document);
		Result result = new StreamResult(new FileOutputStream(file));
		transformer.transform(source, result);
	}
	
	@Test
	public void testOutputXml() throws Exception {
		createToXml(getSmsInfoFromDatabase());
	}
	
//	@Test
	public void testReadXml() throws Exception {
		readFromXml("/test.xml");
	}
	
	public List<SmsInfo> getSmsInfoFromDatabase() throws Exception {
		return new JdbcSqliteSms().getSmsInfo();
	}
	
//	@Test
	public void testCompareSms() throws Exception {
		Map<String, SmsInfo> map = readFromXml("/test.xml");
		List<SmsInfo> list = getSmsInfoFromDatabase();
		int i = 1;
		for (SmsInfo smsInfo : list) {
			// Display values
			System.out.print("[" + i);
			System.out.print("], [" + smsInfo.getPhone());
			System.out.print("], [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(smsInfo.getDate()))));
			System.out.print("], [" + smsInfo.getType());
			System.out.print("], 【" + smsInfo.getBody() + "】");
			System.out.println();
			if (null != map.get(smsInfo.getKey())) {
				// Display values
				System.err.print("[" + i);
				System.err.print("], [" + smsInfo.getPhone());
				System.err.print("], [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(smsInfo.getDate()))));
				System.err.print("], [" + smsInfo.getType());
				System.err.print("], 【" + smsInfo.getBody() + "】");
				System.err.println();
			}
			i++;
		}
	}

}
