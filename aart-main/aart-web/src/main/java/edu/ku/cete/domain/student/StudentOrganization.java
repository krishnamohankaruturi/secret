package edu.ku.cete.domain.student;

public class StudentOrganization {
	private Long attendanceSchoolId;
	private String attendanceSchoolDisplayIdentifier;
	private String attendanceSchoolName;

	private Long districtId;
	private String districtDisplayIdentifier;
	private String districtName;

	private Long stateId;
	private String stateDisplayIdentifier;
	private String stateName;
	
	private String localId;
	private String aypSchoolName;

	public String getAypSchoolName() {
		return aypSchoolName;
	}

	public void setAypSchoolName(String aypSchoolName) {
		this.aypSchoolName = aypSchoolName;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localID) {
		this.localId = localID;
	}

	public Long getAttendanceSchoolid() {
		return attendanceSchoolId;
	}

	public void setAttendanceSchoolid(Long attendanceSchoolid) {
		this.attendanceSchoolId = attendanceSchoolid;
	}

	public String getAttendanceSchoolDisplayIdentifier() {
		return attendanceSchoolDisplayIdentifier;
	}

	public void setAttendanceSchoolDisplayIdentifier(
			String attendanceSchoolDisplayIdentifier) {
		this.attendanceSchoolDisplayIdentifier = attendanceSchoolDisplayIdentifier;
	}

	public String getAttendanceSchoolName() {
		return attendanceSchoolName;
	}

	public void setAttendanceSchoolName(String attendanceSchoolName) {
		this.attendanceSchoolName = attendanceSchoolName;
	}

	public Long getDistrictid() {
		return districtId;
	}

	public void setDistrictid(Long districtid) {
		this.districtId = districtid;
	}

	public String getDistrictDisplayIdentifier() {
		return districtDisplayIdentifier;
	}

	public void setDistrictDisplayIdentifier(String districtDisplayIdentifier) {
		this.districtDisplayIdentifier = districtDisplayIdentifier;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Long getStateid() {
		return stateId;
	}

	public void setStateid(Long stateid) {
		this.stateId = stateid;
	}

	public String getStateDisplayIdentifier() {
		return stateDisplayIdentifier;
	}

	public void setStateDisplayIdentifier(String stateDisplayIdentifier) {
		this.stateDisplayIdentifier = stateDisplayIdentifier;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
