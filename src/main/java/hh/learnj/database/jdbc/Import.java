package hh.learnj.database.jdbc;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class Import {

	public static void main(String[] args) {
		String fileName = "/Users/hunnyhu/Downloads/company.sql";//args[0];
		if (StringUtils.isBlank(fileName)) {
			System.out.println("请输入文件路径。");
			return;
		} else if (!new File(fileName).exists()) {
			System.out.print("文件不存在。");
			return;
		}
		DB.handle(new ImportHandler(fileName));
	}

}
