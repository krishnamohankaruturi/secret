package edu.ku.cete.report.iap;

import java.util.List;

import edu.ku.cete.domain.LinkageLevelSortOrder;
import edu.ku.cete.domain.content.ContentArea;

public class IAPReportContext {
	
	private String studentName;
    private String studentStateIdentifier;
    private String subjectAbbreviatedName;
    private String essentialElementComplete;
    private String planInProgress;
    private String assignedTeslets;
    private String tesletCompleted;
    private String reportDate;
    private String windowName;
    private String studentUserName;
    private String studentPassword;
    private String schoolName;
    private String districtName;
	private List<CriteriaContextData> criteria;
	private List<LinkageLevelSortOrder> allLinkageLevels;
	private ContentArea contentArea;
	private Long schoolYear;
	private Boolean isInstructionallyEmbeddedModel;
    
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentStateIdentifier() {
		return studentStateIdentifier;
	}
	public void setStudentStateIdentifier(String studentStateIdentifier) {
		this.studentStateIdentifier = studentStateIdentifier;
	}
	public String getSubjectAbbreviatedName() {
		return subjectAbbreviatedName;
	}
	public void setSubjectAbbreviatedName(String subjectAbber) {
		this.subjectAbbreviatedName = subjectAbber;
	}
	public String getEssentialElementComplete() {
		return essentialElementComplete;
	}
	public void setEssentialElementComplete(String essentialElementComplete) {
		this.essentialElementComplete = essentialElementComplete;
	}
	public String getPlanInProgress() {
		return planInProgress;
	}
	public void setPlanInProgress(String planInProgress) {
		this.planInProgress = planInProgress;
	}
	public String getAssignedTeslets() {
		return assignedTeslets;
	}
	public void setAssignedTeslets(String assigendTeslets) {
		this.assignedTeslets = assigendTeslets;
	}
	public String getTesletCompleted() {
		return tesletCompleted;
	}
	public void setTesletCompleted(String tesletCompleted) {
		this.tesletCompleted = tesletCompleted;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getWindowName() {
		return windowName;
	}
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}
	public String getStudentUserName() {
		return studentUserName;
	}
	public void setStudentUserName(String studentUserName) {
		this.studentUserName = studentUserName;
	}
	public String getStudentPassword() {
		return studentPassword;
	}
	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}
	
	public List<CriteriaContextData> getCriteria() {
		return criteria;
	}
	public void setCriteria(List<CriteriaContextData> criteria) {
		this.criteria = criteria;
	}
	public List<LinkageLevelSortOrder> getAllLinkageLevels() {
		return allLinkageLevels;
	}
	public void setAllLinkageLevels(List<LinkageLevelSortOrder> allLinkageLevels) {
		this.allLinkageLevels = allLinkageLevels;
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
	public ContentArea getContentArea() {
		return contentArea;
	}
	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}
	public Long getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}
	public Boolean getIsInstructionallyEmbeddedModel() {
		return isInstructionallyEmbeddedModel;
	}
	public void setIsInstructionallyEmbeddedModel(Boolean isInstructionallyEmbeddedModel) {
		this.isInstructionallyEmbeddedModel = isInstructionallyEmbeddedModel;
	}
}
