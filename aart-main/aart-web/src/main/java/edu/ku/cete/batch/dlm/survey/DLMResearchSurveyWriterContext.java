package edu.ku.cete.batch.dlm.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;

public class DLMResearchSurveyWriterContext implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long enrollmentId;
	private Long studentId;
	private Map<TestCollection, List<Test>> testCollectionTests;
	private List<TestSession> testSessions = new ArrayList<TestSession>();
	private String statestudentidentifier;
	private Long attendanceschoolId;
	private Long contentAreaId;
	private Long schoolYear;
	private String studentFirstName;
	private String studentLastName;
	private String currentGradeAbbrName;
	private Long rosterId;
	private String assessmentProgramCode;
	/**
	 * @return the enrollmentId
	 */
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	/**
	 * @param enrollmentId the enrollmentId to set
	 */
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	/**
	 * @return the testCollectionTests
	 */
	public Map<TestCollection, List<Test>> getTestCollectionTests() {
		return testCollectionTests;
	}
	/**
	 * @param testCollectionTests the testCollectionTests to set
	 */
	public void setTestCollectionTests(
			Map<TestCollection, List<Test>> testCollectionTests) {
		this.testCollectionTests = testCollectionTests;
	}
	/**
	 * @return the testSessions
	 */
	public List<TestSession> getTestSessions() {
		return testSessions;
	}
	/**
	 * @param testSessions the testSessions to set
	 */
	public void setTestSessions(List<TestSession> testSessions) {
		this.testSessions = testSessions;
	}
	/**
	 * @return the statestudentidentifier
	 */
	public String getStatestudentidentifier() {
		return statestudentidentifier;
	}
	/**
	 * @param statestudentidentifier the statestudentidentifier to set
	 */
	public void setStatestudentidentifier(String statestudentidentifier) {
		this.statestudentidentifier = statestudentidentifier;
	}
	/**
	 * @return the attendanceschoolId
	 */
	public Long getAttendanceschoolId() {
		return attendanceschoolId;
	}
	/**
	 * @param attendanceschoolId the attendanceschoolId to set
	 */
	public void setAttendanceschoolId(Long attendanceschoolId) {
		this.attendanceschoolId = attendanceschoolId;
	}
	/**
	 * @return the contentAreaId
	 */
	public Long getContentAreaId() {
		return contentAreaId;
	}
	/**
	 * @param contentAreaId the contentAreaId to set
	 */
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	/**
	 * @return the studentFirstName
	 */
	public String getStudentFirstName() {
		return studentFirstName;
	}
	/**
	 * @param studentFirstName the studentFirstName to set
	 */
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	/**
	 * @return the studentLastName
	 */
	public String getStudentLastName() {
		return studentLastName;
	}
	/**
	 * @param studentLastName the studentLastName to set
	 */
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	/**
	 * @return the schoolYear
	 */
	public Long getSchoolYear() {
		return schoolYear;
	}
	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}
	/**
	 * @return the currentGradeAbbrName
	 */
	public String getCurrentGradeAbbrName() {
		return currentGradeAbbrName;
	}
	/**
	 * @param currentGradeAbbrName the currentGradeAbbrName to set
	 */
	public void setCurrentGradeAbbrName(String currentGradeAbbrName) {
		this.currentGradeAbbrName = currentGradeAbbrName;
	}
	public Long getRosterId() {
		return rosterId;
	}
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
	
	public String getAssessmentProgramCode() {
		return this.assessmentProgramCode;
	}
	
	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
		
	}	
}
