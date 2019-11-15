/**
 * 
 */
package edu.ku.cete.domain.api;

/**
 * @author v090n216
 *
 */
public class EnrollmentAPIObject {

	private String recordType;

	private String uniqueStudentId;

	private Long classroomId;

	private Long enrollmentId;
	
	
	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
	public String getUniqueStudentId() {
		return uniqueStudentId;
	}

	public void setUniqueStudentId(String uniqueStudentId) {
		this.uniqueStudentId = uniqueStudentId;
	}
	public Long getClassroomId() {
		return classroomId;
	}

	public void setClassroomId(Long classroomId) {
		this.classroomId = classroomId;
	}
	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	

}
