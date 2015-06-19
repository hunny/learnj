package hh.learnj.designpattern.filter.step3;

import hh.learnj.designpattern.filter.step1.Person;
import hh.learnj.designpattern.filter.step2.Criteria;

import java.util.ArrayList;
import java.util.List;

public class CriteriaFemale implements Criteria {

	@Override
	public List<Person> meetCriteria(List<Person> persons) {
		List<Person> femalePersons = new ArrayList<Person>();

		for (Person person : persons) {
			if ("FEMALE".equalsIgnoreCase(person.getGender())) {
				femalePersons.add(person);
			}
		}
		return femalePersons;
	}
}
