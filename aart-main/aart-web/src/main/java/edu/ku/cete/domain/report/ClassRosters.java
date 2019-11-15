package edu.ku.cete.domain.report;

import java.util.List;

public class ClassRosters {

	

	private String firstName;

	private String lastName;

	private String studentId;

		
    private Questions questions;
    
    private String totalPoints;
    
    private String totalPercentage;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Questions getQuestions() {
		return questions;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}

	public String getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getTotalPercentage() {
		return totalPercentage;
	}

	public void setTotalPercentage(String totalPercentage) {
		this.totalPercentage = totalPercentage;
	}


}
