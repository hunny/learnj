package hh.learnj.enhance.reflect;

import java.util.Date;

public class BeanPoint {
	
	private int key;
	private int value;
	private Date date = new Date();
	
	public BeanPoint(int key, int value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
