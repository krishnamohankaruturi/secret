package edu.ku.cete.domain.common;

import edu.ku.cete.domain.audit.AuditableDomain;

public class PnpAccomodation extends AuditableDomain implements Comparable<PnpAccomodation> {

	private static final long serialVersionUID = 6734356466536261663L;

	private Long id;
	
	private Long parentId;

	private String accomodation;

	private Long categoryId;

	private Long pianacid;

	private String viewOption;

	private Long sortOrder;

	private String attributeContainerName;

	private Long attributeContainerId;

	private String assessmentProgram;

	private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public String getViewOption() {
		return viewOption;
	}

	public void setViewOption(String viewOption) {
		this.viewOption = viewOption;
	}

	public Long getAttributeContainerId() {
		return attributeContainerId;
	}

	public void setAttributeContainerId(Long attributeContainerId) {
		this.attributeContainerId = attributeContainerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccomodation() {
		return accomodation;
	}

	public void setAccomodation(String accomodation) {
		this.accomodation = accomodation;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getPianacid() {
		return pianacid;
	}

	public void setPianacid(Long pianacid) {
		this.pianacid = pianacid;
	}

	public String getAttributeContainerName() {
		return attributeContainerName;
	}

	public void setAttributeContainerName(String attributeContainerName) {
		this.attributeContainerName = attributeContainerName;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public int compareTo(PnpAccomodation o) {
		return this.getSortOrder().compareTo(o.getSortOrder());
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
