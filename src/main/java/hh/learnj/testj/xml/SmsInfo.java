package hh.learnj.testj.xml;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsInfo {
	
	private String phone;
	private String date;
	private String type;
	private String body;
	
	public SmsInfo() {
		this(null, null, null, null);
	}
	
	public SmsInfo(String phone, String date, String type, String body) {
		super();
		this.phone = phone;
		this.date = date;
		this.type = type;
		this.body = body;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public boolean equals(Object object) {
		SmsInfo smsInfo = (SmsInfo)object;
		if (null == smsInfo) {
			return false;
		}
		if ((null == smsInfo.getPhone() && null == this.getPhone()) || (null != smsInfo.getPhone() && smsInfo.getPhone().equals(this.getPhone()))
				&& (null == smsInfo.getDate() && null == this.getDate()) || (null != smsInfo.getDate() && smsInfo.getDate().equals(this.getDate()))
				&& (null == smsInfo.getType() && null == this.getType()) || (null != smsInfo.getDate() && smsInfo.getType().equals(this.getType()))
				&& (null == smsInfo.getBody() && null == this.getBody()) || (null != smsInfo.getDate() && smsInfo.getBody().equals(this.getBody()))) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	public String getKey() {
		return this.getPhone() + this.getDate() + this.getType();
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(this.getPhone());
		buffer.append("][");
		buffer.append((null == this.getDate()) ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(this.getDate()))));
		buffer.append("][");
		buffer.append(this.getType());
		buffer.append("][");
		buffer.append(this.getBody());
		buffer.append("]");
		return buffer.toString();
	}

}
