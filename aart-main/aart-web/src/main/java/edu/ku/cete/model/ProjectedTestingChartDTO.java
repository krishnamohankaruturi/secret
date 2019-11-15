package edu.ku.cete.model;

import java.util.Date;

import edu.ku.cete.util.ProjectionColorCodes;

public class ProjectedTestingChartDTO {

	private String assessmentProgram;
	private Date testDate;
	private Long count;
	private String columnName;
	private Long districtId;
	private Long assessmentProgramId;
	
	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getProjectionType() {
		return projectionType;
	}

	public void setProjectionType(String projectionType) {
		this.projectionType = projectionType;
	}

	private String projectionType;
	
	public void setColorCode(String colorCode) {
	}
	
	public String getColorCode(){
		return ProjectionColorCodes.getByAssessmentCode(getAssessmentProgram()).getColorCode();
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	
}
