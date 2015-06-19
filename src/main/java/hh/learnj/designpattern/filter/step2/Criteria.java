package hh.learnj.designpattern.filter.step2;

import hh.learnj.designpattern.filter.step1.Person;

import java.util.List;

public interface Criteria {
	
	public List<Person> meetCriteria(List<Person> persons);

}
