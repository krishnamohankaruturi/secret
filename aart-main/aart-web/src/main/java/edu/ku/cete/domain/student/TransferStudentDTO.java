package edu.ku.cete.domain.student;

import java.util.Date;

public class TransferStudentDTO {

	private long studentId;
	private String stateStudentIdentifier;
	private String exitWithdrawalType;
	private Date exitWithdrawalDate;
	private String targetAypSchoolIdentifier;
	private Long targetAypSchoolId;
	private Long targetAttendanceSchoolId;
	private Long oldAttendanceSchoolId;
	private Date schoolEntryDate;
	private Date districtEntryDate;
	private String targetDistrictDisplayIdentifier;
	private String targetLocalId;
	private Long targetDistrictIdentifier;

	public String getTargetLocalId() {
		return targetLocalId;
	}

	public void setTargetLocalId(String targetLocalId) {
		this.targetLocalId = targetLocalId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
    
	public String getTargetDistrictDisplayIdentifier() {
		return targetDistrictDisplayIdentifier;
	}

	public void setTargetDistrictDisplayIdentifier(
			String targetDistrictDisplayIdentifier) {
		this.targetDistrictDisplayIdentifier = targetDistrictDisplayIdentifier;
	}
	
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	
	public long getOldAttendanceSchoolId() {
		return oldAttendanceSchoolId;
	}
	
	public void setOldAttendanceSchoolId(long oldAttendanceSchoolId) {
		this.oldAttendanceSchoolId = oldAttendanceSchoolId;
	}

	public String getExitWithdrawalType() {
		return exitWithdrawalType;
	}

	public void setExitWithdrawalType(String exitWithdrawalType) {
		this.exitWithdrawalType = exitWithdrawalType;
	}

	public Date getExitWithdrawalDate() {
		return exitWithdrawalDate;
	}

	public void setExitWithdrawalDate(Date exitWithdrawalDate) {
		this.exitWithdrawalDate = exitWithdrawalDate;
	}

	public String getTargetAypSchoolIdentifier() {
		return targetAypSchoolIdentifier;
	}

	public void setTargetAypSchoolIdentifier(String targetAypSchoolIdentifier) {
		this.targetAypSchoolIdentifier = targetAypSchoolIdentifier;
	}

	public Long getTargetAypSchoolId() {
		return targetAypSchoolId;
	}

	public void setTargetAypSchoolId(Long targetAypSchoolId) {
		this.targetAypSchoolId = targetAypSchoolId;
	}

	public Long getTargetAttendanceSchoolId() {
		return targetAttendanceSchoolId;
	}

	public void setTargetAttendanceSchoolId(Long targetAttendanceSchoolId) {
		this.targetAttendanceSchoolId = targetAttendanceSchoolId;
	}

	
	public Date getSchoolEntryDate() {
		return schoolEntryDate;
	}

	public void setSchoolEntryDate(Date schoolEntryDate) {
		this.schoolEntryDate = schoolEntryDate;
	}

	public Date getDistrictEntryDate() {
		return districtEntryDate;
	}

	public void setDistrictEntryDate(Date districtEntryDate) {
		this.districtEntryDate = districtEntryDate;
	}

	public Long getTargetDistrictIdentifier() {
		return targetDistrictIdentifier;
	}

	public void setTargetDistrictIdentifier(Long targetDistrictIdentifier) {
		this.targetDistrictIdentifier = targetDistrictIdentifier;
	}

}
