package edu.ku.cete.domain;

public class BluePrintCriteriaDescription {
	
	private Long id;
	private String contentAreaAbbrName;
	private Long contentAreaId;
	private String gradeCourseAbbrName;
	private Long gradeCourseId;
	private Long criteria;
	private String criteriaText;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContentAreaAbbrName() {
		return contentAreaAbbrName;
	}
	public void setContentAreaAbbrName(String contentAreaAbbrName) {
		this.contentAreaAbbrName = contentAreaAbbrName;
	}
	public Long getContentAreaId() {
		return contentAreaId;
	}
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	public String getGradeCourseAbbrName() {
		return gradeCourseAbbrName;
	}
	public void setGradeCourseAbbrName(String gradeCourseAbbrName) {
		this.gradeCourseAbbrName = gradeCourseAbbrName;
	}
	public Long getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public Long getCriteria() {
		return criteria;
	}
	public void setCriteria(Long criteria) {
		this.criteria = criteria;
	}
	public String getCriteriaText() {
		return criteriaText;
	}
	public void setCriteriaText(String criteriaText) {
		this.criteriaText = criteriaText;
	}		
}
