package hh.learnj.designpattern.interceptingfilter.step7;

import hh.learnj.designpattern.interceptingfilter.step2.AuthenticationFilter;
import hh.learnj.designpattern.interceptingfilter.step2.DebugFilter;
import hh.learnj.designpattern.interceptingfilter.step3.Target;
import hh.learnj.designpattern.interceptingfilter.step5.FilterManager;
import hh.learnj.designpattern.interceptingfilter.step6.Client;

/**
 * The intercepting filter design pattern is used when we want to do some
 * pre-processing / post-processing with request or response of the application.
 * Filters are defined and applied on the request before passing the request to
 * actual target application. Filters can do the authentication/ authorization/
 * logging or tracking of request and then pass the requests to corresponding
 * handlers. Following are the entities of this type of design pattern.
 * 
 * Filter - Filter which will performs certain task prior or after execution of
 * request by request handler.
 * 
 * Filter Chain - Filter Chain carries multiple filters and help to execute them
 * in defined order on target.
 * 
 * Target - Target object is the request handler
 * 
 * Filter Manager - Filter Manager manages the filters and Filter Chain.
 * 
 * Client - Client is the object who sends request to the Target object.
 * 
 * Implementation We are going to create a FilterChain,FilterManager, Target,
 * Client as various objects representing our entities.AuthenticationFilter and
 * DebugFilter represent concrete filters.
 * 
 * InterceptingFilterDemo, our demo class, will use Client to demonstrate
 * Intercepting Filter Design Pattern.
 * 
 * http://www.tutorialspoint.com/design_pattern/intercepting_filter_pattern.htm
 * 
 * @author hunnyhu
 *
 */
public class InterceptingFilterDemo {

	public static void main(String[] args) {
		FilterManager filterManager = new FilterManager(new Target());
		filterManager.setFilter(new AuthenticationFilter());
		filterManager.setFilter(new DebugFilter());

		Client client = new Client();
		client.setFilterManager(filterManager);
		client.sendRequest("HOME");
	}
}
