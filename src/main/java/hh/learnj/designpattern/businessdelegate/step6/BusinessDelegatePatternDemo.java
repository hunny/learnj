package hh.learnj.designpattern.businessdelegate.step6;

import hh.learnj.designpattern.businessdelegate.step4.BusinessDelegate;
import hh.learnj.designpattern.businessdelegate.step5.Client;

/**
 * Use BusinessDelegate and Client classes to demonstrate Business Delegate
 * pattern.
 * 
 * Business Delegate Pattern is used to decouple presentation tier and business
 * tier. It is basically use to reduce communication or remote lookup
 * functionality to business tier code in presentation tier code. In business
 * tier we have following entities.
 * 
 * Client - Presentation tier code may be JSP, servlet or UI java code.
 * 
 * Business Delegate - A single entry point class for client entities to provide
 * access to Business Service methods.
 * 
 * LookUp Service - Lookup service object is responsible to get relative
 * business implementation and provide business object access to business
 * delegate object.
 * 
 * Business Service - Business Service interface. Concrete classes implement
 * this business service to provide actual business implementation logic.
 * 
 * Implementation We are going to create a Client, BusinessDelegate,
 * BusinessService, LookUpService, JMSService and EJBService representing
 * various entities of Business Delegate patterns.
 * 
 * BusinessDelegatePatternDemo, our demo class, will use BusinessDelegate and
 * Client to demonstrate use of Business Delegate pattern.
 * 
 * @author hunnyhu
 *
 */
public class BusinessDelegatePatternDemo {

	public static void main(String[] args) {

		BusinessDelegate businessDelegate = new BusinessDelegate();
		businessDelegate.setServiceType("EJB");

		Client client = new Client(businessDelegate);
		client.doTask();

		businessDelegate.setServiceType("JMS");
		client.doTask();
	}

}
