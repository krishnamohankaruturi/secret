package edu.ku.cete.domain.enrollment;

import java.io.Serializable;

public class DLMStudentSurveyRosterDetailsDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String stateStudentIdentifier;
	private Long studentId;	
	private Long finalSciBandId;
	private Long finalElaBandId;
	private Long finalMathBandId;
	private Long enrollmentId;
	private Long attendanceSchoolId;
	private Long schoolYear;
	private boolean studentHaveRosters;
	private boolean stateHaveScienceFlagSet;
	private String studentSurveyStatus;
	private String studentFirstName;
	private String studentLastName;
	private String currentGradeAbbrName;
	
	/**
	 * @return the stateStudentIdentifier
	 */
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	/**
	 * @param stateStudentIdentifier the stateStudentIdentifier to set
	 */
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
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
	 * @return the finalSciBandId
	 */
	public Long getFinalSciBandId() {
		return finalSciBandId;
	}
	/**
	 * @param finalSciBandId the finalSciBandId to set
	 */
	public void setFinalSciBandId(Long finalSciBandId) {
		this.finalSciBandId = finalSciBandId;
	}
	/**
	 * @return the finalElaBandId
	 */
	public Long getFinalElaBandId() {
		return finalElaBandId;
	}
	/**
	 * @param finalElaBandId the finalElaBandId to set
	 */
	public void setFinalElaBandId(Long finalElaBandId) {
		this.finalElaBandId = finalElaBandId;
	}
	/**
	 * @return the finalMathBandId
	 */
	public Long getFinalMathBandId() {
		return finalMathBandId;
	}
	/**
	 * @param finalMathBandId the finalMathBandId to set
	 */
	public void setFinalMathBandId(Long finalMathBandId) {
		this.finalMathBandId = finalMathBandId;
	}
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
	 * @return the attendanceSchoolId
	 */
	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}
	/**
	 * @param attendanceSchoolId the attendanceSchoolId to set
	 */
	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
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
	 * @return the studentHaveRosters
	 */
	public boolean isStudentHaveRosters() {
		return studentHaveRosters;
	}
	/**
	 * @param studentHaveRosters the studentHaveRosters to set
	 */
	public void setStudentHaveRosters(boolean studentHaveRosters) {
		this.studentHaveRosters = studentHaveRosters;
	}
	/**
	 * @return the stateHaveScienceFlagSet
	 */
	public boolean isStateHaveScienceFlagSet() {
		return stateHaveScienceFlagSet;
	}
	/**
	 * @param stateHaveScienceFlagSet the stateHaveScienceFlagSet to set
	 */
	public void setStateHaveScienceFlagSet(boolean stateHaveScienceFlagSet) {
		this.stateHaveScienceFlagSet = stateHaveScienceFlagSet;
	}
	/**
	 * @return the studentSurveyStatus
	 */
	public String getStudentSurveyStatus() {
		return studentSurveyStatus;
	}
	/**
	 * @param studentSurveyStatus the studentSurveyStatus to set
	 */
	public void setStudentSurveyStatus(String studentSurveyStatus) {
		this.studentSurveyStatus = studentSurveyStatus;
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
}
