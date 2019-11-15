package edu.ku.cete.model;

import java.util.Date;

import edu.ku.cete.domain.audit.AuditableDomain;

public class ProjectedTestingDTO extends AuditableDomain {

	/**
	 * Added By Sudhansu Feature: f183 Projected Testing
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long assessmentProgramId;
	private Long schoolId;
	private Long districtId;
	private Long stateid;
	private Date testDate;
	private String month;
	private String schoolName;
	private String districtName;
	private Long currentSchoolYear;
	private String modifiedBy;
	/**
	 * Feature: F605 Projected Scoring
	 */
	private String projectionType;
	private String gradeName;
	private Long grade;

	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getProjectionType() {
		return projectionType;
	}

	public void setProjectionType(String projectionType) {
		this.projectionType = projectionType;
	}

	public Long getCurrentSchoolYear() {
		return currentSchoolYear;
	}

	public void setCurrentSchoolYear(Long currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getStateid() {
		return stateid;
	}

	public void setStateid(Long stateid) {
		this.stateid = stateid;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj instanceof ProjectedTestingDTO) {
			if (this.assessmentProgramId.longValue() == ((ProjectedTestingDTO) obj).assessmentProgramId.longValue()
					&& this.schoolId.longValue() == ((ProjectedTestingDTO) obj).schoolId.longValue()) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
