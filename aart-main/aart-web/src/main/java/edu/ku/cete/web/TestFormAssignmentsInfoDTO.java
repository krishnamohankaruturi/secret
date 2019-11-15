package edu.ku.cete.web;

import java.util.Date;

/*
 * Added during US16344 : for Extracting reports on TEst Form assign to TestCollection for quality check
 */
public class TestFormAssignmentsInfoDTO {

	private String formName;
	private String testName;
	private Date formPublicationDate;	
	private Long formIdContentBuilder;
	private Long formIdEducatorPortal;
	private Long CBTestCollevtionId;
	private String CBTestCollectionName;
	private Long overViewId;
	private String overViewName;
	private String overViewPhase;
	private String overViewPool;
	private String stage;
	private String panelName;
	private Long panelId;
	private String testletId;
	private String qCCompleteStatusFlag;
	private String formsAccessibilityFlagField;

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Long getFormIdContentBuilder() {
		return formIdContentBuilder;
	}

	public void setFormIdContentBuilder(Long formIdContentBuilder) {
		this.formIdContentBuilder = formIdContentBuilder;
	}

	public Long getFormIdEducatorPortal() {
		return formIdEducatorPortal;
	}

	public void setFormIdEducatorPortal(Long formIdEducatorPortal) {
		this.formIdEducatorPortal = formIdEducatorPortal;
	}
	
	public String getTestname() {
		return testName;
	}

	public void setTestname(String testname) {
		this.testName = testname;
	}

	public Date getFormPublicationDate() {
		return formPublicationDate;
	}

	public void setFormPublicationDate(Date formPublicationDate) {
		this.formPublicationDate = formPublicationDate;
	}


	public Long getCBTestCollevtionId() {
		return CBTestCollevtionId;
	}

	public void setCBTestCollevtionId(Long cBTestCollevtionId) {
		CBTestCollevtionId = cBTestCollevtionId;
	}

	public String getCBTestCollectionName() {
		return CBTestCollectionName;
	}

	public void setCBTestCollectionName(String cBTestCollectionName) {
		CBTestCollectionName = cBTestCollectionName;
	}

	public Long getOverViewId() {
		return overViewId;
	}

	public void setOverViewId(Long overViewId) {
		this.overViewId = overViewId;
	}

	public String getOverViewName() {
		return overViewName;
	}

	public void setOverViewName(String overViewName) {
		this.overViewName = overViewName;
	}

	public String getOverViewPhase() {
		return overViewPhase;
	}

	public void setOverViewPhase(String overViewPhase) {
		this.overViewPhase = overViewPhase;
	}

	public String getOverViewPool() {
		return overViewPool;
	}

	public void setOverViewPool(String overViewPool) {
		this.overViewPool = overViewPool;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPanelName() {
		return panelName;
	}

	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}

	public Long getPanelId() {
		return panelId;
	}

	public void setPanelId(Long panelId) {
		this.panelId = panelId;
	}

	public String getTestletId() {
		return testletId+"  ";
	}

	public void setTestletId(String testletId) {
		this.testletId = testletId;
	}

	public String getqCCompleteStatusFlag() {
		return qCCompleteStatusFlag;
	}

	public void setqCCompleteStatusFlag(String qCCompleteStatusFlag) {
		this.qCCompleteStatusFlag = qCCompleteStatusFlag;
	}

	public String getFormsAccessibilityFlagField() {
		return formsAccessibilityFlagField;
	}

	public void setFormsAccessibilityFlagField(
			String formsAccessibilityFlagField) {
		this.formsAccessibilityFlagField = formsAccessibilityFlagField;
	}

	public Date getFormLastModifiedDate() {
		return formLastModifiedDate;
	}

	public void setFormLastModifiedDate(Date formLastModifiedDate) {
		this.formLastModifiedDate = formLastModifiedDate;
	}

	private Date formLastModifiedDate;

}
