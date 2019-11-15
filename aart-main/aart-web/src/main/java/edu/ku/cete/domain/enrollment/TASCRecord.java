/**
 * 
 */
package edu.ku.cete.domain.enrollment;

import java.util.Date;

/**
 * @author ktaduru_sta
 *
 */
public class TASCRecord extends TASCRosterRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8776667547840825969L;

	private Date createDate; 	
	private String recordCommonId;
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRecordCommonId() {
		return recordCommonId;
	}
	public void setRecordCommonId(String recordCommonId) {
		this.recordCommonId = recordCommonId;
	}
	public String getAttendanceSchoolIdentifier() {
		return getSchoolIdentifier();
	}
	public void setAttendanceSchoolIdentifier(String attendanceSchoolIdentifier) {
		this.setSchoolIdentifier(attendanceSchoolIdentifier);
	}
	public Integer getTascStudentGender() {
		return getStudent().getGender();
	}
	public void setTascStudentGender(Integer studentGender) {
		getStudent().setGender(studentGender);
	}
	
	public String getEducatorSchoolId() {
		return getEducator().getSchoolID();
	}
	public void setEducatorSchoolId(String educatorSchoolId) {
		getEducator().setSchoolID(educatorSchoolId);;
	}
	public String getTascStateSubjectAreaCode() {
		return getStateSubjectAreaCode();
	}
	public void setTascStateSubjectAreaCode(String tascStateSubjectAreaCode) {
		this.setStateSubjectAreaCode(tascStateSubjectAreaCode);
	}
	public Long getTascLocalCourseId() {
		return getLocalCourseId();
	}
	public void setTascLocalCourseId(Long tascLocalCourseId) {
		this.setLocalCourseId(tascLocalCourseId);
	}	
	
	public String getGenerationCode() {
		return getStudent().getGenerationCode();
	}
	
	public void setGenerationCode(String generationCodeChar) {
		getStudent().setGenerationCode(generationCodeChar);
	}
}
