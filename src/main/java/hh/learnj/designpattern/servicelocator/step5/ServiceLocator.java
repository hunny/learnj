package hh.learnj.designpattern.servicelocator.step5;

import hh.learnj.designpattern.servicelocator.step1.Service;
import hh.learnj.designpattern.servicelocator.step3.InitialContext;
import hh.learnj.designpattern.servicelocator.step4.Cache;

public class ServiceLocator {
	
	private static Cache cache;

	static {
		cache = new Cache();
	}

	public static Service getService(String jndiName) {
		Service service = cache.getService(jndiName);
		if (service != null) {
			return service;
		}
		InitialContext context = new InitialContext();
		Service service1 = (Service) context.lookup(jndiName);
		cache.addService(service1);
		return service1;
	}
}
