/**
 * 
 */
package hh.learnj.test.license.test.alps;

import java.util.Date;

/**
 * license值
 * 
 * @author huzexiong
 */
public class License {
	
	private Date begin = null;
	private Date end = null;
	private Integer number =  null;
	
	/**
	 * @return the begin
	 */
	public Date getBegin() {
		return begin;
	}
	/**
	 * @param begin the begin to set
	 */
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	/**
	 * @return the end
	 */
	public Date getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(Date end) {
		this.end = end;
	}
	/**
	 * @return the number
	 */
	public Integer getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

}
