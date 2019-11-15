package edu.ku.cete.domain.student.survey;

public class DLMAutoRegistrationDTO {
	private Long surveyId;
	private Long studentId;
	private String firstName;
	private String lastName;
	private Long finalELABandId;
	private Long finalMathBandId;
	private String currentGradeLevelCode;
	private Long rosterId;
	
	public Long getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
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
	public Long getFinalELABandId() {
		return finalELABandId;
	}
	public void setFinalELABandId(Long finalELABandId) {
		this.finalELABandId = finalELABandId;
	}
	public Long getFinalMathBandId() {
		return finalMathBandId;
	}
	public void setFinalMathBandId(Long finalMathBandId) {
		this.finalMathBandId = finalMathBandId;
	}
	public String getCurrentGradeLevelCode() {
		return currentGradeLevelCode;
	}
	public void setCurrentGradeLevelCode(String currentGradeLevelCode) {
		this.currentGradeLevelCode = currentGradeLevelCode;
	}
	public Long getRosterId() {
		return rosterId;
	}
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
}
