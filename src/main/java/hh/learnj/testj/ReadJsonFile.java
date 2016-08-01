package hh.learnj.testj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class ReadJsonFile {
	
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	protected String readFromFile(String fileName) {
		StringBuilder builder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			File file = new File(fileName);
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
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
		}
		return builder.toString();
	}
	
	@Test
	public void testLoadJson() {
		String result = readFromFile("C:/work/src/main/ext/bootstrap-test.json");
		JSONObject json = new JSONObject(result);
		JSONObject paths = (JSONObject)json.get("paths");
		for (String key : paths.keySet()) {
			String value = paths.getString(key);
			if (value.endsWith(".js")) {
				System.out.println("\"" + value + "\",");
			}
		}
		JSONArray arr = json.getJSONArray("js");
		for (int i = 0; i < arr.length(); i++) {
			JSONObject object = arr.getJSONObject(i);
			String value = object.getString("path");
			System.out.println("\"" + value + "\",");
		}
		logger.info(arr);
	}

}
