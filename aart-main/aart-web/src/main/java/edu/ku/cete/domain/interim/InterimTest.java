package edu.ku.cete.domain.interim;

import java.util.Date;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * @author venkat
 *
 */
public class InterimTest extends AuditableDomain {

	/**
	 * Serial Version ID.
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private Long gradeCourseId;
	private Long contentAreaId;
	private Boolean activeFlag;

	private String assembledBy;
	private Long[] miniTestIds;
	private String miniTestIdsString;
	private Date createDate;
	private Date modifiedDate;
	private Long createdUser;
	private Long modifiedUser;
	private String dateCreated;

	private Long testTestId;
	private Long testCollectionId;

	private Boolean isTestAssigned;
	private Boolean isScoringComplete;

	private String schoolName;

	private Long organizationId;
	
	private Long purposeId;
	private String purposeName;

	private String testSessionId;
	private Boolean isTestCopied;
	private Long currentSchoolYear;

	public String getAssembledBy() {
		return assembledBy;
	}

	public void setAssembledBy(String assembledBy) {
		this.assembledBy = assembledBy;
	}

	public Long[] getMiniTestIds() {
		return miniTestIds;
	}

	public void setMiniTestIds(Long[] miniTestIds) {
		this.miniTestIds = miniTestIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getGradeCourseId() {
		return gradeCourseId;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getMiniTestIdsString() {
		return miniTestIdsString;
	}

	public void setMiniTestIdsString(String miniTestIdsString) {
		this.miniTestIdsString = miniTestIdsString;
	}

	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Long getTestTestId() {
		return testTestId;
	}

	public void setTestTestId(Long testId) {
		this.testTestId = testId;
	}

	public Long getTestCollectionId() {
		return testCollectionId;
	}

	public void setTestCollectionId(Long testCollectionId) {
		this.testCollectionId = testCollectionId;
	}

	public Boolean getIsTestAssigned() {
		return isTestAssigned;
	}

	public void setIsTestAssigned(Boolean isTestAssigned) {
		this.isTestAssigned = isTestAssigned;
	}

	public Boolean getIsScoringComplete() {
		return isScoringComplete;
	}

	public void setIsScoringComplete(Boolean isScoringComplete) {
		this.isScoringComplete = isScoringComplete;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getTestSessionId() {
		return testSessionId;
	}

	public void setTestSessionId(String testSessionId) {
		this.testSessionId = testSessionId;
	}

	public Boolean getIsTestCopied() {
		return isTestCopied;
	}

	public void setIsTestCopied(Boolean isTestCopied) {
		this.isTestCopied = isTestCopied;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(Long purposeId) {
		this.purposeId = purposeId;
	}

	public String getPurposeName() {
		return purposeName;
	}

	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}

	public Long getCurrentSchoolYear() {
		return currentSchoolYear;
	}

	public void setCurrentSchoolYear(Long currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}

	// Add all the Properties to this Class.
}
