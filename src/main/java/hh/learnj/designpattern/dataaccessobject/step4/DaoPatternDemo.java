package hh.learnj.designpattern.dataaccessobject.step4;

import hh.learnj.designpattern.dataaccessobject.step1.Student;
import hh.learnj.designpattern.dataaccessobject.step2.StudentDao;
import hh.learnj.designpattern.dataaccessobject.step3.StudentDaoImpl;

/**
 * Use the StudentDao to demonstrate Data Access Object pattern usage.
 * 
 * Data Access Object Pattern or DAO pattern is used to separate low level data
 * accessing API or operations from high level business services. Following are
 * the participants in Data Access Object Pattern.
 * 
 * Data Access Object Interface - This interface defines the standard operations
 * to be performed on a model object(s).
 * 
 * Data Access Object concrete class - This class implements above interface.
 * This class is responsible to get data from a data source which can be
 * database / xml or any other storage mechanism.
 * 
 * Model Object or Value Object - This object is simple POJO containing get/set
 * methods to store data retrieved using DAO class.
 * 
 * Implementation We are going to create a Student object acting as a Model or
 * Value Object.StudentDao is Data Access Object Interface.StudentDaoImpl is
 * concrete class implementing Data Access Object Interface. DaoPatternDemo, our
 * demo class, will use StudentDao to demonstrate the use of Data Access Object
 * pattern.
 * 
 * http://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm
 * 
 * @author hunnyhu
 *
 */
public class DaoPatternDemo {

	public static void main(String[] args) {

		StudentDao studentDao = new StudentDaoImpl();

		// print all students
		for (Student student : studentDao.getAllStudents()) {
			System.out.println("Student: [RollNo : " + student.getRollNo()
					+ ", Name : " + student.getName() + " ]");
		}

		// update student
		Student student = studentDao.getAllStudents().get(0);
		student.setName("Michael");
		studentDao.updateStudent(student);

		// get the student
		studentDao.getStudent(0);
		System.out.println("Student: [RollNo : " + student.getRollNo()
				+ ", Name : " + student.getName() + " ]");
	}
}
