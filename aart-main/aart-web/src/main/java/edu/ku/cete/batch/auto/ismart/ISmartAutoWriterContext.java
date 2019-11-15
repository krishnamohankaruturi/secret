/**
 * 
 */
package edu.ku.cete.batch.auto.ismart;

import java.io.Serializable;
import java.util.List;

import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;

/**
 * @author Kiran Reddy Taduru
 * Jun 6, 2018 4:08:07 PM
 */
public class ISmartAutoWriterContext implements Serializable {
	
	private static final long serialVersionUID = 4923471039917938887L;
	private Long rosterId;
	private Long enrollmentId;
	private Long studentId;
	private TestCollection testCollection;
	private List<Test> tests;
	private Long gradeCourseId;
	private String gradeCourseAbbrName;
	private String statestudentidentifier;
	private String attSchDisplayIdentifer;
	private Long attendanceschoolId;
	private Long contentAreaId;
	private String contentAreaAbbrName;
	private String studentFirstName;
	private String studentLastName;
	private TestSession testSession;
	
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
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}	
	public TestCollection getTestCollection() {
		return testCollection;
	}
	public void setTestCollection(TestCollection testCollection) {
		this.testCollection = testCollection;
	}	
	public List<Test> getTests() {
		return tests;
	}
	public void setTests(List<Test> tests) {
		this.tests = tests;
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
	public TestSession getTestSession() {
		return testSession;
	}
	public void setTestSession(TestSession testSession) {
		this.testSession = testSession;
	}
	
	
}
