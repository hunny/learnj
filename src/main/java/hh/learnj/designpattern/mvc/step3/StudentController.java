package hh.learnj.designpattern.mvc.step3;

import hh.learnj.designpattern.mvc.step1.Student;
import hh.learnj.designpattern.mvc.step2.StudentView;

public class StudentController {

	private Student model;
	
	private StudentView view;

	public StudentController(Student model, StudentView view) {
		this.model = model;
		this.view = view;
	}

	public void setStudentName(String name) {
		model.setName(name);
	}

	public String getStudentName() {
		return model.getName();
	}

	public void setStudentRollNo(String rollNo) {
		model.setRollNo(rollNo);
	}

	public String getStudentRollNo() {
		return model.getRollNo();
	}

	public void updateView() {
		view.printStudentDetails(model.getName(), model.getRollNo());
	}

}
