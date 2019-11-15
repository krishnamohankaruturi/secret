package edu.ku.cete.web;

import java.util.List;

public class DLMBlueprintCoverageReportDTO {
	private Long orgId;
	private String orgName;
	
	private Long teacherId;
	private String teacherFirstName;
	private String teacherLastName;
	
	private Long contentAreaId;
	private String contentAreaName;
	
	private Long gradeCourseId;
	private String gradeCourseName;
	
	private List<BlueprintCriteriaAndEEDTO> criteriaAndEEs;
	
	private List<DLMBlueprintCoverageReportStudentTestsCriteriaDTO> studentTestCriteria;
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherFirstName() {
		return teacherFirstName;
	}
	public void setTeacherFirstName(String teacherFirstName) {
		this.teacherFirstName = teacherFirstName;
	}
	public String getTeacherLastName() {
		return teacherLastName;
	}
	public void setTeacherLastName(String teacherLastName) {
		this.teacherLastName = teacherLastName;
	}
	public Long getContentAreaId() {
		return contentAreaId;
	}
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	public String getContentAreaName() {
		return contentAreaName;
	}
	public void setContentAreaName(String contentAreaName) {
		this.contentAreaName = contentAreaName;
	}
	public Long getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public String getGradeCourseName() {
		return gradeCourseName;
	}
	public void setGradeCourseName(String gradeCourseName) {
		this.gradeCourseName = gradeCourseName;
	}
	public List<BlueprintCriteriaAndEEDTO> getCriteriaAndEEs() {
		return criteriaAndEEs;
	}
	public void setCriteriaAndEEs(List<BlueprintCriteriaAndEEDTO> criteriaAndEEs) {
		this.criteriaAndEEs = criteriaAndEEs;
	}
	public List<DLMBlueprintCoverageReportStudentTestsCriteriaDTO> getStudentTestCriteria() {
		return studentTestCriteria;
	}
	public void setStudentTestCriteria(
			List<DLMBlueprintCoverageReportStudentTestsCriteriaDTO> studentTestCriteria) {
		this.studentTestCriteria = studentTestCriteria;
	}
}