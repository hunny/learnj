package hh.learnj.database.jdbc.mybatis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.jdbc.ScriptRunner;

public class RunSqlScript {

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {

		String aSQLScriptFilePath = "path/to/sql/script.sql";
		// Create MySql Connection
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/database", "username", "password");
		Statement stmt = null;

		try {
			// Initialize object for ScripRunner
			ScriptRunner sr = new ScriptRunner(con);
			// Give the input file to Reader
			Reader reader = new BufferedReader(new FileReader(
					aSQLScriptFilePath));
			// Exctute script
			sr.runScript(reader);
		} catch (Exception e) {
			System.err.println("Failed to Execute" + aSQLScriptFilePath
					+ " The error is " + e.getMessage());
		}
	}

}
