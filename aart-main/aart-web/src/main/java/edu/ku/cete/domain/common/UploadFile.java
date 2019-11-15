/**
 * 
 */
package edu.ku.cete.domain.common;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author m802r921
 *
 */
public class UploadFile {
	@JsonIgnore	
	private File file;

	private long selectedRecordTypeId;
	
	private long stateId;
	
	private long regionId;
	
	private long areaId;
	
	private long districtId;
	
	private long buildingId;
	
	private long schoolId;
	
	private long rosterUpload;
	
	private Boolean continueOnWarning;
	
	private long selectedOrgId;	

	private Long operationalWindowId;

	public Boolean getContinueOnWarning() {
		return continueOnWarning;
	}

	public void setContinueOnWarning(Boolean continueOnWarning) {
		this.continueOnWarning = continueOnWarning;
	}

	/**
	 * @param uploadedFileData the fileData to set
	 */
//	public final void setFileData(CommonsMultipartFile uploadedFileData) {
//		this.fileData = uploadedFileData;
//	}

	/**
	 * @return the fileData
	 */
//	@JsonIgnore
//	public final CommonsMultipartFile getFileData() {
//		return fileData;
//	}

	/**
	 * @return the selectedRecordTypeId
	 */
	public final long getSelectedRecordTypeId() {
		return selectedRecordTypeId;
	}

	/**
	 * @param selectedRecordTypeId the selectedRecordTypeId to set
	 */
	public final void setSelectedRecordTypeId(long selectedRecordTypeId) {
		this.selectedRecordTypeId = selectedRecordTypeId;
	}

	/**
	 * @return the stateId
	 */
	public long getStateId() {
		return stateId;
	}

	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the districtId
	 */
	public long getDistrictId() {
		return districtId;
	}

	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(long districtId) {
		this.districtId = districtId;
	}

	/**
	 * @return the schoolId
	 */
	public long getSchoolId() {
		return schoolId;
	}

	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(long schoolId) {
		this.schoolId = schoolId;
	}

	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}

	public long getAreaId() {
		return areaId;
	}

	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}

	public long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(long buildingId) {
		this.buildingId = buildingId;
	}

	/**
	 * @return the isRosterUpload
	 */
	public long getRosterUpload() {
		return rosterUpload;
	}

	/**
	 * @param isRosterUpload the isRosterUpload to set
	 */
	public void setRosterUpload(long isRosterUpload) {
		this.rosterUpload = isRosterUpload;
	}

	public long getSelectedOrgId() {
		return selectedOrgId;
	}

	public void setSelectedOrgId(long selectedOrgId) {
		this.selectedOrgId = selectedOrgId;
	}

	@JsonIgnore
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Long getOperationalWindowId() {
		return operationalWindowId;
	}

	public void setOperationalWindowId(Long operationalWindowId) {
		this.operationalWindowId = operationalWindowId;
	}
}
