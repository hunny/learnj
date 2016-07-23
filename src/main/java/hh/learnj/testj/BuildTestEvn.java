package hh.learnj.testj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class BuildTestEvn {
	
	private final String ENTER = "\r\n";
	private static String BASE_DIR = "C:/work/";
	
	@BeforeClass
	public static void beforeTest() {
		/**
		 * Need to set Run Configurations of VM arguments, eg: -DbaseDir=C:/work/ext
		 */
		String baseDir = System.getProperty("baseDir");
		if (null != baseDir) {
			BASE_DIR = baseDir;
		}
	}
	
	protected boolean buildFile(String srcFileName, String destFileName, LineHandler lineHandler) {
		File srcFile = new File(BASE_DIR + File.separator + srcFileName);
		if (!srcFile.exists()) {
			return false;
		}
		File destFile = new File(BASE_DIR + File.separator + destFileName);
		if (srcFile.getPath().equals(destFile.getPath())) {
			throw new IllegalArgumentException("The same file can not be builded.");
		}
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(srcFile));
			bufferedWriter = new BufferedWriter(new FileWriter(destFile, false));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				String tmp = lineHandler.checkLine(line);
				if (null == tmp) {
					tmp = line;
				}
				bufferedWriter.write(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != bufferedWriter) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	@Test
	public void testBootstrapDotJs() {
		buildFile("bootstrap.js", "bootstrap-test.js", new LineHandler() {

			@Override
			public String checkLine(String line) {
				if (null == line) {
					return ENTER;
				}
				String tmp = line;
				if (line.matches(".*Ext\\.manifest\\s*=\\s*Ext\\.manifest\\s*\\|\\|\\s*\"bootstrap\\.json\";.*")) {
					tmp = tmp.replaceFirst("bootstrap\\.json", "bootstrap-test.json");
				} else if (line.matches(".*return\\s*this\\.css\\.concat\\(this\\.js\\);\\s*")) {
					tmp = tmp.replaceFirst("return\\s*this\\.css\\.concat\\(this\\.js\\);", "var array = this.css.concat(this.js); if(this.tests) return array.concat(this.tests); return array;");
				} else if (line.matches(".*this\\.js\\s*=\\s*this\\.processAssets\\(this\\.content\\.js,\\s*'js'\\);\\s*")) {
					tmp = tmp + ENTER + "this.tests = this.processAssets(this.content.tests, 'js');";
				}
				return tmp + ENTER;
			}
		});
	}
	
	@Test
	public void testBootstrapDotJson() {
		buildFile("bootstrap.json", "bootstrap-test.json", new LineHandler() {

			@Override
			public String checkLine(String line) {
				if (null == line) {
					return ENTER;
				}
				String tmp = line;
				boolean matchAppJs = line.matches(".*\\{\"path\"\\s*:\\s*\"app\\.js\"\\}.*");
				if (matchAppJs) {
					tmp = tmp.replaceFirst("\\{\"path\"\\s*:\\s*\"app\\.js\"\\}", "{\"path\":\"app-test.js\"}");
				}
				boolean matchTests = line.matches("\"tests\"\\s*:\\s*\\[\\{\\s*\"");
				if (!matchTests) {
					tmp = tmp.replaceFirst("}$", ",\"tests\":[{\"bootstrap\":true,\"path\":\"tests/specs/describes.js\"}]}");
				}
				return tmp + ENTER;
			}
		});
	}

}
