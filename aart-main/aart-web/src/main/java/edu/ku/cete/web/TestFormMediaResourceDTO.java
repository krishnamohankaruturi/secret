package edu.ku.cete.web;


import java.util.Date;

public class TestFormMediaResourceDTO {
	
	private String formName;
	private Long contentBuilderFormId;
	private Long educatorPortalFormId;
	private String testName;
	private String testCollection;
	private String resourceFileName;
	private String contentBuilderMediaName;
	private Long contentBuilderMediaId;
	private Long sectionNumber;
	private Date lastModifiedDate;
	private Date createDate;
	private String testletId;
	private String qcCompleteStatus;
	
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getQcCompleteStatus() {
		return qcCompleteStatus;
	}
	public void setQcCompleteStatus(String qcCompleteStatus) {
		this.qcCompleteStatus = qcCompleteStatus;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public Long getContentBuilderFormId() {
		return contentBuilderFormId;
	}
	public void setContentBuilderFormId(Long contentBuilderFormId) {
		this.contentBuilderFormId = contentBuilderFormId;
	}
	public Long getEducatorPortalFormId() {
		return educatorPortalFormId;
	}
	public void setEducatorPortalFormId(Long educatorPortalFormId) {
		this.educatorPortalFormId = educatorPortalFormId;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getTestCollection() {
		return testCollection;
	}
	public void setTestCollection(String testCollection) {
		this.testCollection = testCollection;
	}
	public String getResourceFileName() {
		return resourceFileName;
	}
	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}
	public String getContentBuilderMediaName() {
		return contentBuilderMediaName;
	}
	public void setContentBuilderMediaName(String contentBuilderMediaName) {
		this.contentBuilderMediaName = contentBuilderMediaName;
	}
	public Long getContentBuilderMediaId() {
		return contentBuilderMediaId;
	}
	public void setContentBuilderMediaId(Long contentBuilderMediaId) {
		this.contentBuilderMediaId = contentBuilderMediaId;
	}
	public Long getSectionNumber() {
		return sectionNumber;
	}
	public void setSectionNumber(Long sectionNumber) {
		this.sectionNumber = sectionNumber;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public String getTestletId() {
		return testletId+"  ";
	}
	public void setTestletId(String testletId) {
		this.testletId = testletId;
	}
	
	
}
