package edu.ku.cete.web;

import java.util.Date;

public class UserSecurityAgreemntDTO {

	private Long userId;
	private String state;
	private String district;
	private String building;
	private String lastName;
	private String firstName;
	private String middleName;
	private String securityAgreementStatus;
	private String securityAgreementAcceptedOn;
	private String securityAgreementSignatureStatus;
	private Date securityAgreementSignedOn;
	private String roles;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSecurityAgreementStatus() {
		return securityAgreementStatus;
	}

	public void setSecurityAgreementStatus(String securityAgreementStatus) {
		this.securityAgreementStatus = securityAgreementStatus;
	}

	public String getSecurityAgreementAcceptedOn() {
		return securityAgreementAcceptedOn;
	}

	public void setSecurityAgreementAcceptedOn(String securityAgreementAcceptedOn) {
		this.securityAgreementAcceptedOn = securityAgreementAcceptedOn;
	}

	public String getSecurityAgreementSignatureStatus() {
		return securityAgreementSignatureStatus;
	}

	public void setSecurityAgreementSignatureStatus(String securityAgreementSignatureStatus) {
		this.securityAgreementSignatureStatus = securityAgreementSignatureStatus;
	}

	public Date getSecurityAgreementSignedOn() {
		return securityAgreementSignedOn;
	}

	public void setSecurityAgreementSignedOn(Date securityAgreementSignedOn) {
		this.securityAgreementSignedOn = securityAgreementSignedOn;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
