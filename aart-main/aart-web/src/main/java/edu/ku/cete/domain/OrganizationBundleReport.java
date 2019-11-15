package edu.ku.cete.domain;

import edu.ku.cete.domain.audit.AuditableDomain;

/* sudhansu.b
 * Added for F460 - Dynamic bundle report
 */
public class OrganizationBundleReport extends AuditableDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long organizationId;
	private Long status;
	private Long assessmentProgramId;
	private String filePath;
	private Long schoolYear;
	private Boolean activeFlag;
	private Boolean bySchool;
	private Boolean separateFile;
	private String schoolIds;
	private String subjects;
	private String grades;
	private String schoolNames;
	private String subjectNames;
	private String gradeNames;
	private String sort1;
	private String sort2;
	private String sort3;
	private String submittedUser;
	private String statusString;
	private String sortString;
	private String reportType;
	private String reportTypeCode;
	
	public String getSortString() {
		return sortString;
	}

	public void setSortString(String sortString) {
		this.sortString = sortString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Boolean getBySchool() {
		return bySchool;
	}

	public void setBySchool(Boolean bySchool) {
		this.bySchool = bySchool;
	}

	public Boolean getSeparateFile() {
		return separateFile;
	}

	public void setSeparateFile(Boolean separateFile) {
		this.separateFile = separateFile;
	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public String getGrades() {
		return grades;
	}

	public void setGrades(String grades) {
		this.grades = grades;
	}

	public String getSort1() {
		return sort1;
	}

	public void setSort1(String sort1) {
		this.sort1 = sort1;
	}

	public String getSort2() {
		return sort2;
	}

	public void setSort2(String sort2) {
		this.sort2 = sort2;
	}

	public String getSort3() {
		return sort3;
	}

	public void setSort3(String sort3) {
		this.sort3 = sort3;
	}

	public String getSchoolIds() {
		return schoolIds;
	}

	public void setSchoolIds(String schoolIds) {
		this.schoolIds = schoolIds;
	}

	public String getSubjectNames() {
		return subjectNames;
	}

	public void setSubjectNames(String subjectNames) {
		this.subjectNames = subjectNames;
	}

	public String getSchoolNames() {
		return schoolNames;
	}

	public void setSchoolNames(String schoolNames) {
		this.schoolNames = schoolNames;
	}

	public String getGradeNames() {
		return gradeNames;
	}

	public void setGradeNames(String gradeNames) {
		this.gradeNames = gradeNames;
	}

	public String getSubmittedUser() {
		return submittedUser;
	}

	public void setSubmittedUser(String submittedUser) {
		this.submittedUser = submittedUser;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportTypeCode() {
		return reportTypeCode;
	}

	public void setReportTypeCode(String reportTypeCode) {
		this.reportTypeCode = reportTypeCode;
	}

}
