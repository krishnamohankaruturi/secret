package edu.ku.cete.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.util.ParsingConstants;

public class OperationalTestWindowStudentRecord extends ValidateableRecord implements Serializable {

	private static final long serialVersionUID = 941431449962068411L;
	private String exclude;
	private String subject;
	private String course;
	private String stateStudentIdentifier;
	private String attendanceSchoolProgramIdentifier;
	private String organization;
	private String lineNumber;
	private String failedReason;
	private OperationalTestWindowStudent operationalTestWindowStudent = new OperationalTestWindowStudent();

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public String getAttendanceSchoolProgramIdentifier() {
		return attendanceSchoolProgramIdentifier;
	}

	public void setAttendanceSchoolProgramIdentifier(String attendanceSchoolProgramIdentifier) {
		this.attendanceSchoolProgramIdentifier = attendanceSchoolProgramIdentifier;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public OperationalTestWindowStudent getOperationalTestWindowStudent() {
		return operationalTestWindowStudent;
	}

	public void setOperationalTestWindowStudent(OperationalTestWindowStudent operationalTestWindowStudent) {
		this.operationalTestWindowStudent = operationalTestWindowStudent;
	}

	@Override
	public String getIdentifier() {
		return ParsingConstants.BLANK + this.getStateStudentIdentifier();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Long getId() {
		return getOperationalTestWindowStudent().getStudentId();
	}

	public Long getId(int order) {
		return getId();
	}
	
	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

}
