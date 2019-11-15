package edu.ku.cete.domain.report;

import java.util.List;

import edu.ku.cete.report.domain.AssessmentTopic;
import edu.ku.cete.report.domain.OrganizationPrctTopicReportsDTO;
import edu.ku.cete.report.domain.StudentPrctTopicReportsDTO;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public class ExternalSchoolDetailReportDTO{
	private Long schoolYear;
	private String gradeCode;
	private String gradeName;
	private String subject;
	private String assessmentCode;
	private Long schoolId;
	private String schoolDisplayIdentifier;
	private String schoolName;
	private Long districtId;
	private String districtName;
	private String districtDisplayIdentifier;
	private Long stateId;
	private String stateDisplayIdentifier;
	private String stateName;
	
	private List<AssessmentTopic> topicList;
	private List<StudentPrctTopicReportsDTO> studentList;
	private List<OrganizationPrctTopicReportsDTO> organizationList;
	
	
	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getAssessmentCode() {
		return assessmentCode;
	}

	public void setAssessmentCode(String assessmentCode) {
		this.assessmentCode = assessmentCode;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<AssessmentTopic> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<AssessmentTopic> topicList) {
		this.topicList = topicList;
	}

	public List<StudentPrctTopicReportsDTO> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<StudentPrctTopicReportsDTO> studentList) {
		this.studentList = studentList;
	}

	public List<OrganizationPrctTopicReportsDTO> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List<OrganizationPrctTopicReportsDTO> organizationList) {
		this.organizationList = organizationList;
	}

	public String getDistrictDisplayIdentifier() {
		return districtDisplayIdentifier;
	}

	public void setDistrictDisplayIdentifier(String districtDisplayIdentifier) {
		this.districtDisplayIdentifier = districtDisplayIdentifier;
	}

	public String getSchoolDisplayIdentifier() {
		return schoolDisplayIdentifier;
	}

	public void setSchoolDisplayIdentifier(String schoolDisplayIdentifier) {
		this.schoolDisplayIdentifier = schoolDisplayIdentifier;
	}

	public String getStateDisplayIdentifier() {
		return stateDisplayIdentifier;
	}

	public void setStateDisplayIdentifier(String stateDisplayIdentifier) {
		this.stateDisplayIdentifier = stateDisplayIdentifier;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
