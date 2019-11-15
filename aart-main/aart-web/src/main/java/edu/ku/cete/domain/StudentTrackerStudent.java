package edu.ku.cete.domain;

import java.io.Serializable;

public class StudentTrackerStudent implements Serializable{

	private static final long serialVersionUID = -8657718030184878065L;
	private Long id;
	private Long studentId;
	private Long finalBandId;
	private Long gradeId;
	private Long enrollmentId;
	private Long rosterId;
	private Long enrollmentRosterId;
	private Long attendanceSchoolId;
	private Integer currentSchoolYear;
	private String legalFirstName;
	private String legalLastName;
	private String attendanceSchoolName;
	private String attendanceSchoolIdentifier;
	private Long studentBandRecommendationId;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the finalBandId
	 */
	public Long getFinalBandId() {
		return finalBandId;
	}
	/**
	 * @param finalBandId the finalBandId to set
	 */
	public void setFinalBandId(Long finalBandId) {
		this.finalBandId = finalBandId;
	}
	/**
	 * @return the gradeId
	 */
	public Long getGradeId() {
		return gradeId;
	}
	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public Long getRosterId() {
		return rosterId;
	}
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
	public Long getEnrollmentRosterId() {
		return enrollmentRosterId;
	}
	public void setEnrollmentRosterId(Long enrollmentRosterId) {
		this.enrollmentRosterId = enrollmentRosterId;
	}
	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}
	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}
	public Integer getCurrentSchoolYear() {
		return currentSchoolYear;
	}
	public void setCurrentSchoolYear(Integer currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}
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
	public String getAttendanceSchoolName() {
		return attendanceSchoolName;
	}
	public void setAttendanceSchoolName(String attendanceSchoolName) {
		this.attendanceSchoolName = attendanceSchoolName;
	}
	public String getAttendanceSchoolIdentifier() {
		return attendanceSchoolIdentifier;
	}
	public void setAttendanceSchoolIdentifier(String attendanceSchoolIdentifier) {
		this.attendanceSchoolIdentifier = attendanceSchoolIdentifier;
	}
	public Long getStudentBandRecommendationId() {
		return studentBandRecommendationId;
	}
	public void setStudentBandRecommendationId(Long studentBandRecommendationId) {
		this.studentBandRecommendationId = studentBandRecommendationId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
}
