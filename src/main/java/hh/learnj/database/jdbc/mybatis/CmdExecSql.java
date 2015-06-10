package hh.learnj.database.jdbc.mybatis;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CmdExecSql {

	public static void main(String[] args) {
		try {
			String line = null;
			Process p = Runtime
					.getRuntime()
					.exec("psql -U username -d dbname -h serverhost -f scripfile.sql");
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

}
