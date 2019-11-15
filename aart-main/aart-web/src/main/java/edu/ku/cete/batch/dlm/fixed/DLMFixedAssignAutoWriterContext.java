package edu.ku.cete.batch.dlm.fixed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;

public class DLMFixedAssignAutoWriterContext implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long rosterId;
	private Long enrollmentId;
	private Long studentId;
	private Map<TestCollection, List<Test>> testCollectionTests;
	private List<TestSession> testSessions = new ArrayList<TestSession>();
	private Long gradeCourseId;
	private String gradeCourseAbbrName;
	private String statestudentidentifier;
	private String attSchDisplayIdentifer;
	private Long attendanceschoolId;
	private Long contentAreaId;
	private String contentAreaAbbrName;
	private String studentFirstName;
	private String studentLastName;
	
	public Long getRosterId() {
		return rosterId;
	}
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public Map<TestCollection, List<Test>> getTestCollectionTests() {
		return testCollectionTests;
	}
	public void setTestCollectionTests(
			Map<TestCollection, List<Test>> testCollectionTests) {
		this.testCollectionTests = testCollectionTests;
	}
	public List<TestSession> getTestSessions() {
		return testSessions;
	}
	public void setTestSessions(List<TestSession> testSessions) {
		this.testSessions = testSessions;
	}
	public Long getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public String getGradeCourseAbbrName() {
		return gradeCourseAbbrName;
	}
	public void setGradeCourseAbbrName(String gradeCourseAbbrName) {
		this.gradeCourseAbbrName = gradeCourseAbbrName;
	}
	public String getStatestudentidentifier() {
		return statestudentidentifier;
	}
	public void setStatestudentidentifier(String statestudentidentifier) {
		this.statestudentidentifier = statestudentidentifier;
	}
	public String getAttSchDisplayIdentifer() {
		return attSchDisplayIdentifer;
	}
	public void setAttSchDisplayIdentifer(String attSchDisplayIdentifer) {
		this.attSchDisplayIdentifer = attSchDisplayIdentifer;
	}
	public Long getAttendanceschoolId() {
		return attendanceschoolId;
	}
	public void setAttendanceschoolId(Long attendanceschoolId) {
		this.attendanceschoolId = attendanceschoolId;
	}
	public Long getContentAreaId() {
		return contentAreaId;
	}
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	public String getContentAreaAbbrName() {
		return contentAreaAbbrName;
	}
	public void setContentAreaAbbrName(String contentAreaAbbrName) {
		this.contentAreaAbbrName = contentAreaAbbrName;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
}
