package edu.ku.cete.web;

import java.util.Date;

public class DLMPDTrainingDTO {

	private String userName;
	private String lastName;
	private String firstName;
	private String email;
	private String idNumber;
	private String password;
	private String state;
	private String districtName;
	private String districtId;
	private String schoolName;
	private String schoolId;
	private String role;
	private Date createdDate;
	private String rtComplete;
	private Date rtCompleteDate;
	private long userOrganizationGroupId;
	// F730
	private String userTrainingType;

	public String getRtComplete() {
		return rtComplete;
	}

	public void setRtComplete(String rtComplete) {
		this.rtComplete = rtComplete;
	}

	public Date getRtCompleteDate() {
		return rtCompleteDate;
	}

	public void setRtCompleteDate(Date rtCompleteDate) {
		this.rtCompleteDate = rtCompleteDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUseeName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastname(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public long getUserOrganizationGroupId() {
		return userOrganizationGroupId;
	}

	public void setUserOrganizationGroupId(long userOrganizationGroupId) {
		this.userOrganizationGroupId = userOrganizationGroupId;
	}

	// F730
	public String getUserTrainingType() {
		return userTrainingType;
	}

	public void setUserTrainingType(String userTrainingType) {
		this.userTrainingType = userTrainingType;
	}
}
