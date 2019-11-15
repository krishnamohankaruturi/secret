package edu.ku.cete.domain.interim;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentActivityReport {

	private String firstName;
	private String lastName;
	private String studentId;
	private String status;
	private String test;
	private String totalPoints;
	private String totalPercentage;
	private Date testCompletionDate;
	private Date testAssignedDate;
	private String studentGrade;
	private String testSubject;
	private String testGrade;

	public String getFirstName() {
		if(firstName==null){
			firstName="-";
		}
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		if(lastName==null){
			lastName="-";
		}
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStudentId() {
		if(studentId==null){
			studentId="-";
		}
		return studentId;
	}

	public void setStudentId(String studentId) {
		
		this.studentId = studentId;
	}

	public String getTotalPoints() {
		if(totalPoints==null){
			totalPoints="-";
		}
		return totalPoints;
	}

	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getTotalPercentage() {
		if(totalPercentage==null){
			return totalPercentage="-";
		}
		return totalPercentage+"%";
	}

	public void setTotalPercentage(String totalPercentage) {
		this.totalPercentage = totalPercentage;
	}

	public String getStatus() {
		if(status==null){
			status="-";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTest() {
		if(test==null){
			test="-";
		}
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getTestCompletionDate() {
		if (this.testCompletionDate != null) {
			SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");
			return df2.format(this.testCompletionDate);
		} else {
			return "-";
		}
	}

	public void setTestCompletionDate(Date testCompletionDate) {
		this.testCompletionDate = testCompletionDate;
	}

	public String getStudentGrade() {
		if(studentGrade==null){
			studentGrade="-";
		}
		return studentGrade;
	}

	public void setStudentGrade(String studentGrade) {
		this.studentGrade = studentGrade;
	}

	public String getTestSubject() {
		if(testSubject==null){
			testSubject="-";
		}
		return testSubject;
	}

	public void setTestSubject(String testSubject) {
		this.testSubject = testSubject;
	}

	public String getTestGrade() {
		if(testGrade==null){
			testGrade="-";
		}
		return testGrade;
	}

	public void setTestGrade(String testGrade) {
		this.testGrade = testGrade;
	}

	public String getTestAssignedDate() {
		if (this.testAssignedDate != null) {
			SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");
			return df2.format(this.testAssignedDate);
		} else {
			return "-";
		}
	}

	public void setTestAssignedDate(Date testAssignedDate) {
		this.testAssignedDate = testAssignedDate;
	}

}
