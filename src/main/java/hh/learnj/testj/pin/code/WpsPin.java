package hh.learnj.testj.pin.code;


public interface WpsPin {

	public void setBssid(String mac);
	
	public void setSsid(String ssid);
	
	public String getRooterName();
	
	public String getPinCode();
	
}
