package edu.ku.cete.domain;

public class InterimPredictiveQuestionExtractDTO {
	private String legalFirstName;
	private String legalLastName;
	private String stateStudentIdentifier;
	private String results;
	private String districtDisplayIdentifier;
	private String districtName;
	private String schoolDisplayIdentifier;
	private String schoolName;
	private String reportCycle;
	private String subject;
	private String grade;
	private String gradeName;
	private Long interimStudentReportId;

	public String getLegalFirstName() {
		return legalFirstName;
	}

	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}

	public String getLegalLastName() {
		return legalLastName;
	}

	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getDistrictDisplayIdentifier() {
		return districtDisplayIdentifier;
	}

	public void setDistrictDisplayIdentifier(String districtDisplayIdentifier) {
		this.districtDisplayIdentifier = districtDisplayIdentifier;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getSchoolDisplayIdentifier() {
		return schoolDisplayIdentifier;
	}

	public void setSchoolDisplayIdentifier(String schoolDisplayIdentifier) {
		this.schoolDisplayIdentifier = schoolDisplayIdentifier;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getReportCycle() {
		return reportCycle;
	}

	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Long getInterimStudentReportId() {
		return interimStudentReportId;
	}

	public void setInterimStudentReportId(Long interimStudentReportId) {
		this.interimStudentReportId = interimStudentReportId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

}
