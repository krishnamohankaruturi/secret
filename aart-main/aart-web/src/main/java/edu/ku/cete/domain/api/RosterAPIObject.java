package edu.ku.cete.domain.api;


public class RosterAPIObject {
	 private String recordType;
	 
	 private String educatorUniqueId;
	 
	 private Long classroomId;
	 
	 private String schoolIdentifier;
	 
	 private String courseCode;

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getEducatorUniqueId() {
		return educatorUniqueId;
	}

	public void setEducatorUniqueId(String educatorUniqueId) {
		this.educatorUniqueId = educatorUniqueId;
	}

	public Long getClassroomId() {
		return classroomId;
	}

	public void setClassroomId(Long classroomId) {
		this.classroomId = classroomId;
	}

	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}

	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

}
