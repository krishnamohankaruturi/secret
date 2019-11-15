package edu.ku.cete.web;

import java.util.Date;

import edu.ku.cete.domain.report.OrganizationReportDetails;

public class OrganizationReportsImportDTO extends ReportsImportDTO {
	private Long organizationId;
	private Long teacherID;
	private Long stateId;

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	
	public Long getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(Long teacherID) {
		this.teacherID = teacherID;
	}	
	
	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}	
	
	public OrganizationReportDetails toOrganizationReportDetails(){
		Date date = new Date();
		OrganizationReportDetails details = new OrganizationReportDetails();
		details.setAssessmentProgramId(getAssessmentProgramId());
		details.setBatchReportProcessId(getBatchReportProcessId());
		details.setContentAreaId(getContentAreaId());
		details.setDetailedReportPath(getPdfFilePath());
		details.setCsvDetailedReportPath(getCsvFilePath());
		details.setDistrictDisplayIdentifier(getDistrict());
		details.setGradeCourseAbbrName(getGrade());
		details.setGradeId(getGradeId());
		details.setOrganizationId(getOrganizationId());
		details.setSchoolYear(getSchoolYear());
		details.setReportType(getReportType());
		details.setTeacherId(getTeacherID());
		details.setSchoolName(getCurrentSchoolName());
		details.setDistrictName(getCurrentDistrictName());
		details.setCreatedUser(getCreatedUser());
		details.setModifiedUser(getModifiedUser());
		details.setModifiedDate(date);
		details.setCreatedDate(date);
		return details;
	}

}
