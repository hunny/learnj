package hh.learnj.designpattern.dataaccessobject.step2;

import hh.learnj.designpattern.dataaccessobject.step1.Student;

import java.util.List;

public interface StudentDao {
	
	public List<Student> getAllStudents();

	public Student getStudent(int rollNo);

	public void updateStudent(Student student);

	public void deleteStudent(Student student);
}
