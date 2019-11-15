package edu.ku.cete.domain.common;

import java.io.Serializable;

public class OrganizationTreeDetail  implements Serializable{
	private static final long serialVersionUID = -8372801857208411845L;
	private Long schoolId;
	private String schoolName;
	private String schoolDisplayIdentifier;
	private Long districtId;
	private String districtName;
	private String districtDisplayIdentifier;
	private Long stateId;
	private String stateName ;
	private String stateDisplayIdentifier;
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolDisplayIdentifier() {
		return schoolDisplayIdentifier;
	}
	public void setSchoolDisplayIdentifier(String schoolDisplayIdentifier) {
		this.schoolDisplayIdentifier = schoolDisplayIdentifier;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getDistrictDisplayIdentifier() {
		return districtDisplayIdentifier;
	}
	public void setDistrictDisplayIdentifier(String districtDisplayIdentifier) {
		this.districtDisplayIdentifier = districtDisplayIdentifier;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getStateDisplayIdentifier() {
		return stateDisplayIdentifier;
	}
	public void setStateDisplayIdentifier(String stateDisplayIdentifier) {
		this.stateDisplayIdentifier = stateDisplayIdentifier;
	}
}
