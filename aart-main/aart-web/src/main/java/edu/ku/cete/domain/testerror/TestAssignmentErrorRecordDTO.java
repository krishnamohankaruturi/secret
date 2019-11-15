/**
	 * 
	 * Changes for F845 Test Assignment Errors Dashboard
	 */

package edu.ku.cete.domain.testerror;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.ku.cete.util.DateUtil;

public class TestAssignmentErrorRecordDTO {
	
	private static final long serialVersionUID = 3350215087863991083L;
	
	private Date createdDate;
	
	private String ssid;
	
	private String message;
	
	private String school;

	private String course;
	
	private Long classroomId;
	
	private String educatorId;
	
	private String studentFirstName;
	
	private String studentLastName;
	
	private String educatorFirstName;
	
	private String educatorLastName;
	
	public String getCreatedDate() {
		
		if(this.createdDate == null) {
			return StringUtils.EMPTY;
		}		
		return DateUtil.format(this.createdDate,"MM/dd/yy hh:mm a z");
		
	}
	
	@JsonProperty("Date")
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getSsid() {
		return ssid;
	}

	@JsonProperty("SSID")
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getMessage() {
		return message;
	}

	@JsonProperty("Message")
	public void setMessage(String message) {
		this.message = message;
	}

	public String getSchool() {
		return school;
	}
	
	@JsonProperty("School")
	public void setSchool(String school) {
		this.school = school;
	}

	public String getCourse() {
		return course;
	}

	@JsonProperty("Course")
	public void setCourse(String course) {
		this.course = course;
	}

	public Long getClassroomId() {
		return classroomId;
	}

	@JsonProperty("Classroom ID")
	public void setClassroomId(Long classroomId) {
		this.classroomId = classroomId;
	}

	public String getEducatorId() {
		return educatorId;
	}

	@JsonProperty("Educator ID")
	public void setEducatorId(String educatorId) {
		this.educatorId = educatorId;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}
	
	@JsonProperty("Student First Name")
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}
	
	@JsonProperty("Student Last Name")
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}

	public String getEducatorFirstName() {
		return educatorFirstName;
	}
	
	@JsonProperty("Educator First Name")
	public void setEducatorFirstName(String educatorFirstName) {
		this.educatorFirstName = educatorFirstName;
	}

	public String getEducatorLastName() {
		return educatorLastName;
	}
	
	@JsonProperty("Educator Last Name")
	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}


}
