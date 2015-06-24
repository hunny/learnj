package hh.learnj.designpattern.interceptingfilter.step4;

import hh.learnj.designpattern.interceptingfilter.step1.Filter;
import hh.learnj.designpattern.interceptingfilter.step3.Target;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {
	
	private List<Filter> filters = new ArrayList<Filter>();
	private Target target;

	public void addFilter(Filter filter) {
		filters.add(filter);
	}

	public void execute(String request) {
		for (Filter filter : filters) {
			filter.execute(request);
		}
		target.execute(request);
	}

	public void setTarget(Target target) {
		this.target = target;
	}
}
