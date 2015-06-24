package hh.learnj.designpattern.interceptingfilter.step5;

import hh.learnj.designpattern.interceptingfilter.step1.Filter;
import hh.learnj.designpattern.interceptingfilter.step3.Target;
import hh.learnj.designpattern.interceptingfilter.step4.FilterChain;

public class FilterManager {
	
	private FilterChain filterChain;

	public FilterManager(Target target) {
		filterChain = new FilterChain();
		filterChain.setTarget(target);
	}

	public void setFilter(Filter filter) {
		filterChain.addFilter(filter);
	}

	public void filterRequest(String request) {
		filterChain.execute(request);
	}
}
