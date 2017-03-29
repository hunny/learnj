package hh.learnj.database.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hh.learnj.database.jdbc.DB.Hander;

public class ImportHandler implements Hander {

	private final Logger logger = LoggerFactory.getLogger(ImportHandler.class);
	private String fileName;

	public ImportHandler(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void handle(Statement stmt) throws SQLException {
		BufferedReader bufferedReader = null;
		try {
			File file = new File(fileName);
			bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				try {
					stmt.executeUpdate(line);
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("异常语句{}", line);
				}
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
	}

}
