package hh.learnj.designpattern.interceptingfilter.step6;

import hh.learnj.designpattern.interceptingfilter.step5.FilterManager;

public class Client {
	
	private FilterManager filterManager = null;

	public void setFilterManager(FilterManager filterManager) {
		this.filterManager = filterManager;
	}

	public void sendRequest(String request) {
		filterManager.filterRequest(request);
	}
}
