package edu.ku.cete.web;

import java.util.HashSet;
import java.util.Set;

public class DLMTestAdminMonitoringSummaryDTO {
	private String districtIdentifier;
	private String districtName;
	private Long districtId;
	private String schoolIdentifier;
	private String schoolName;
	private Long schoolId;
	private String grade;
	private Long gradeId;
	private String subject;
	private Long subjectId;
	
	private Set<Long> studentsWithInstructionallyEmbeddedNoPlansCreated = new HashSet<Long>();
	private Set<Long> studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTaken = new HashSet<Long>();
	private Set<Long> studentsWithInstructionallyEmbeddedOnlyOneTestletCompleted = new HashSet<Long>();
	private Set<Long> studentsWithInstructionallyEmbeddedMoreThanOneTestletCompleted = new HashSet<Long>();
	private Set<Long> studentsWithInstructionallyEmbeddedNoPlansCreatedSecondWindow = new HashSet<Long>();
	private Set<Long> studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTakenSecondWindow = new HashSet<Long>();
	private Set<Long> studentsWithInstructionallyEmbeddedOnlyOneTestletCompletedSecondWindow = new HashSet<Long>();
	private Set<Long> studentsWithInstructionallyEmbeddedMoreThanOneTestletCompletedSecondWindow = new HashSet<Long>();
	private Set<Long> studentsWithYearEndNoTestletsTaken = new HashSet<Long>();
	private Set<Long> studentsWithYearEndTestingInProgress = new HashSet<Long>();
	private Set<Long> studentsWithYearEndAllRequiredTestletsCompleted = new HashSet<Long>();
	
	public String getDistrictIdentifier() {
		return districtIdentifier;
	}
	public void setDistrictIdentifier(String districtIdentifier) {
		this.districtIdentifier = districtIdentifier;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}
	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public int getNumberOfStudentsWithInstructionallyEmbeddedNoPlansCreated() {
		return this.studentsWithInstructionallyEmbeddedNoPlansCreated.size();
	}
	public int getNumberOfStudentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTaken() {
		return this.studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTaken.size();
	}
	public int getNumberOfStudentsWithInstructionallyEmbeddedOnlyOneTestletCompleted() {
		return this.studentsWithInstructionallyEmbeddedOnlyOneTestletCompleted.size();
	}
	public int getNumberOfStudentsWithInstructionallyEmbeddedMoreThanOneTestletCompleted() {
		return this.studentsWithInstructionallyEmbeddedMoreThanOneTestletCompleted.size();
	}
	public int getNumberOfStudentsWithYearEndNoTestletsTaken() {
		return this.studentsWithYearEndNoTestletsTaken.size();
	}
	public int getNumberOfStudentsWithYearEndTestingInProgress() {
		return this.studentsWithYearEndTestingInProgress.size();
	}
	public int getNumberOfStudentsWithYearEndAllRequiredTestletsCompleted() {
		return this.studentsWithYearEndAllRequiredTestletsCompleted.size();
	}
	public Set<Long> getStudentsWithInstructionallyEmbeddedNoPlansCreated() {
		return this.studentsWithInstructionallyEmbeddedNoPlansCreated;
	}
	public Set<Long> getStudentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTaken() {
		return this.studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTaken;
	}
	public Set<Long> getStudentsWithInstructionallyEmbeddedOnlyOneTestletCompleted() {
		return this.studentsWithInstructionallyEmbeddedOnlyOneTestletCompleted;
	}
	public Set<Long> getStudentsWithInstructionallyEmbeddedMoreThanOneTestletCompleted() {
		return this.studentsWithInstructionallyEmbeddedMoreThanOneTestletCompleted;
	}
	public Set<Long> getStudentsWithYearEndNoTestletsTaken() {
		return this.studentsWithYearEndNoTestletsTaken;
	}
	public Set<Long> getStudentsWithYearEndTestingInProgress() {
		return this.studentsWithYearEndTestingInProgress;
	}
	public Set<Long> getStudentsWithYearEndAllRequiredTestletsCompleted() {
		return this.studentsWithYearEndAllRequiredTestletsCompleted;
	}
	public Set<Long> getStudentsWithInstructionallyEmbeddedNoPlansCreatedSecondWindow() {
		return this.studentsWithInstructionallyEmbeddedNoPlansCreatedSecondWindow;
	}
	public Set<Long> getStudentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTakenSecondWindow() {
		return this.studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTakenSecondWindow;
	}
	public Set<Long> getStudentsWithInstructionallyEmbeddedOnlyOneTestletCompletedSecondWindow() {
		return this.studentsWithInstructionallyEmbeddedOnlyOneTestletCompletedSecondWindow;
	}
	public Set<Long> getStudentsWithInstructionallyEmbeddedMoreThanOneTestletCompletedSecondWindow() {
		return this.studentsWithInstructionallyEmbeddedMoreThanOneTestletCompletedSecondWindow;
	}
	public int getNumberOfStudentsWithInstructionallyEmbeddedNoPlansCreatedSecondWindow() {
		return this.studentsWithInstructionallyEmbeddedNoPlansCreatedSecondWindow.size();
	}
	public int getNumberOfStudentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTakenSecondWindow() {
		return this.studentsWithInstructionallyEmbeddedPlansCreatedNoTestletsTakenSecondWindow.size();
	}
	public int getNumberOfStudentsWithInstructionallyEmbeddedOnlyOneTestletCompletedSecondWindow() {
		return this.studentsWithInstructionallyEmbeddedOnlyOneTestletCompletedSecondWindow.size();
	}
	public int getNumberOfStudentsWithInstructionallyEmbeddedMoreThanOneTestletCompletedSecondWindow() {
		return this.studentsWithInstructionallyEmbeddedMoreThanOneTestletCompletedSecondWindow.size();
	}
}