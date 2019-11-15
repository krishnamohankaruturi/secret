package edu.ku.cete.domain;

import java.io.Serializable;
import java.util.Date;
/*
 * Sudhansu : F61 - manage organization
 */
public class OrganizationSnapshot implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long organizationId;
	private String organizationName;
	private String displayIdentifier;
	private Long organizationTypeId;
	private Long parentSnapshotId;
	private Long parentOrganiationId;
	private Long parentOrganizationTypeId;
	private String parentDisplayIdentifier;
	private String assessemntPrograms;
	private String testingModel;
	private Date schoolStartDate;
	private Date schoolEndDate;
	private Boolean contractingOrganization;
	private Long schoolYear;
	private Date snapshotCreatedDate;
	private Long snapshotCreatedUser;
	private Boolean activeFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getDisplayIdentifier() {
		return displayIdentifier;
	}

	public void setDisplayIdentifier(String displayIdentifier) {
		this.displayIdentifier = displayIdentifier;
	}

	public Long getOrganizationTypeId() {
		return organizationTypeId;
	}

	public void setOrganizationTypeId(Long organizationTypeId) {
		this.organizationTypeId = organizationTypeId;
	}

	public Long getParentSnapshotId() {
		return parentSnapshotId;
	}

	public void setParentSnapshotId(Long parentSnapshotId) {
		this.parentSnapshotId = parentSnapshotId;
	}

	public Long getParentOrganiationId() {
		return parentOrganiationId;
	}

	public void setParentOrganiationId(Long parentOrganiationId) {
		this.parentOrganiationId = parentOrganiationId;
	}

	public String getParentDisplayIdentifier() {
		return parentDisplayIdentifier;
	}

	public void setParentDisplayIdentifier(String parentDisplayIdentifier) {
		this.parentDisplayIdentifier = parentDisplayIdentifier;
	}

	public String getAssessemntPrograms() {
		return assessemntPrograms;
	}

	public void setAssessemntPrograms(String assessemntPrograms) {
		this.assessemntPrograms = assessemntPrograms;
	}

	public String getTestingModel() {
		return testingModel;
	}

	public void setTestingModel(String testingModel) {
		this.testingModel = testingModel;
	}

	public Date getSchoolStartDate() {
		return schoolStartDate;
	}

	public void setSchoolStartDate(Date schoolStartDate) {
		this.schoolStartDate = schoolStartDate;
	}

	public Date getSchoolEndDate() {
		return schoolEndDate;
	}

	public void setSchoolEndDate(Date schoolEndDate) {
		this.schoolEndDate = schoolEndDate;
	}

	public Boolean getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Boolean contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Date getSnapshotCreatedDate() {
		return snapshotCreatedDate;
	}

	public void setSnapshotCreatedDate(Date snapshotCreatedDate) {
		this.snapshotCreatedDate = snapshotCreatedDate;
	}

	public Long getSnapshotCreatedUser() {
		return snapshotCreatedUser;
	}

	public void setSnapshotCreatedUser(Long snapshotCreatedUser) {
		this.snapshotCreatedUser = snapshotCreatedUser;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getParentOrganizationTypeId() {
		return parentOrganizationTypeId;
	}

	public void setParentOrganizationTypeId(Long parentOrganizationTypeId) {
		this.parentOrganizationTypeId = parentOrganizationTypeId;
	}
}
