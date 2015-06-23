package hh.learnj.testj.pin.code.data.collect;

import hh.learnj.testj.pin.code.sqlite.JdbcSqliteUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class CrackFastPinCode {
	
	private static Logger logger = Logger.getLogger(CrackFastPinCode.class);
	
//	再上一段同批次的 TP路由器跟FAST路由器的
//
//	MAC: 8C210A512946  PIN: 89354018
//	MAC: 8C210A512930  PIN: 46828149
//	MAC: 8C210A51292C  PIN: 48787833
//	MAC: 8C210A51291A  PIN: 40742779
//	MAC: 8C210A5125D2  PIN: 44526931
//	MAC: 8C210A5124F8  PIN: 69955266
//	MAC: 8C210A5124F8  PIN: 69955266
//	MAC: 8C210A5123E8  PIN: 69788031
//	MAC: 8C210A5114F8  PIN: 27268957
//	MAC: 8C210A5114D0  PIN: 86167765
//	MAC: 8C210A511494  PIN: 68246389
//	MAC: 8C210A51148C  PIN: 58259603
//	MAC: 8C210A511484  PIN: 52820045
//	MAC: 8C210A47701C  PIN: 11917656
//	MAC: 8C210A475C08  PIN: 16728233
//	MAC: 8C210A475BF6  PIN: 40471754
//	MAC: 8C210A39A4E8  PIN: 26254392
//	MAC: 8C210A39A48E  PIN: 20167585
//	MAC: 8C210A399FF2  PIN: 19056937
//	MAC: 8C210A396E38  PIN: 16327696
//	MAC: 8C210A396E32  PIN: 61513570
//	MAC: 8C210A396DC6  PIN: 24066648
//	MAC: 8C210A396D34  PIN: 51034247
//	MAC: 8C210A395B8C  PIN: 70542363
//	MAC: 8C210A395B86  PIN: 69344125
//	MAC: 8C210A395B80  PIN: 96838198
//	MAC: 8C210A395B74  PIN: 20285302
//	MAC: 8C210A395AE6  PIN: 14845604
//	MAC: 8C210A395ADC  PIN: 38999055
//	MAC: 8C210A395996  PIN: 93579742
//	MAC: 8C210A2F46A2  PIN: 51471576
//	MAC: 388345B25B36  PIN: 82679682
//	MAC: 388345B25A9C  PIN: 27623749
//	MAC: 14E6E4BE45EA  PIN: 96555958
//	MAC: 14E6E4BE45DC  PIN: 87454055
//	MAC: 14E6E4A9A010  PIN: 97518129
//	MAC: 14E6E4A9A008  PIN: 46207227
//	MAC: 14E6E4A99ED4  PIN: 82612771
//	MAC: 14E6E4A98D7C  PIN: 45415210
//	MAC: 14E6E4A97FEE  PIN: 82059095
//	MAC: 14E6E4A97F26  PIN: 84200037
//	MAC: 14E6E4563ED4  PIN: 38831881
//	MAC: 14E6E451D6B0  PIN: 16505032
//	MAC: 14E6E451B2B2  PIN: 80545156
//	MAC: 14E6E44FC340  PIN: 47794900
//	MAC: 14E6E44FC1E4  PIN: 38691416
//	MAC: 14E6E44FBEBC  PIN: 89246047
//	MAC: 14E6E44F89B4  PIN: 74325566
//	MAC: 14E6E44D58EA  PIN: 53398796
//	MAC: 14E6E44D575A  PIN: 11890768
//	MAC: 14E6E44CD696  PIN: 19104645
//	MAC: 14E6E44CC0A0  PIN: 97541592
//	MAC: 14E6E44CBD3A  PIN: 29554966
//	MAC: 14E6E43D7928  PIN: 80713173
//	MAC: 14E6E43D782F  PIN: 95617800
//	MAC: 14E6E43D7A20  PIN: 32074789
	
	public static void main(String [] args) {
		URL url = CrackFastPinCode.class.getResource(".");
		String path = url.getPath();
		path = path.replaceAll("target/classes/", "src/main/java/");
		path += "CrackFastPinCode.java";
		File file = new File(path);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			JdbcSqliteUtil util = new JdbcSqliteUtil();
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				if (null != line && line.startsWith("//	MAC")) {
					String tmp = line.replaceFirst("//	MAC: ", "").replaceAll(" PIN: ", "");
					logger.debug(tmp);
					String [] arr = tmp.split(" ");
					String BSSID = arr[0].substring(0, 2) 
							+ ":" + arr[0].substring(2, 4) 
							+ ":" + arr[0].substring(4, 6) 
							+ ":" + arr[0].substring(6, 8) 
							+ ":" + arr[0].substring(8, 10) 
							+ ":" + arr[0].substring(10, 12);
					String PIN = arr[1];
					logger.debug(BSSID + ", " + PIN);
					List<Map<String, Object>> list = util.query("select * from reaver_wash where bssid = '" + BSSID + "'");
					if (null == list || list.isEmpty()) {
						Map<String, Object> values = new HashMap<String, Object>();
						values.put("BSSID", BSSID);
						values.put("PIN", PIN);
						util.saveToWashReaver(values);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(path);
	}

}
