/**
 * 
 */
package edu.ku.cete.domain;

/**
 * @author ktaduru_sta
 *
 */
public class StudentTestInfo {

	private Long id;
	private Long studentId;
	private String testStatus;
	private Long testSessionId;
	private Double interimTheta;
	private Long previousStudentsTestId;
	private Long testCollectionId;
	private Long testId;
	private Long panelId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	public String getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}
	public Long getTestSessionId() {
		return testSessionId;
	}
	public void setTestSessionId(Long testSessionId) {
		this.testSessionId = testSessionId;
	}
	public Double getInterimTheta() {
		return interimTheta;
	}
	public void setInterimTheta(Double interimTheta) {
		this.interimTheta = interimTheta;
	}
	public Long getPreviousStudentsTestId() {
		return previousStudentsTestId;
	}
	public void setPreviousStudentsTestId(Long previousStudentsTestId) {
		this.previousStudentsTestId = previousStudentsTestId;
	}
	public Long getTestCollectionId() {
		return testCollectionId;
	}
	public void setTestCollectionId(Long testCollectionId) {
		this.testCollectionId = testCollectionId;
	}
	public Long getTestId() {
		return testId;
	}
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	public Long getPanelId() {
		return panelId;
	}
	public void setPanelId(Long panelId) {
		this.panelId = panelId;
	}
	
}
