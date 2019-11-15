package edu.ku.cete.domain.student.survey;

import edu.ku.cete.domain.audit.AuditableDomain;

public class SurveyLabel extends AuditableDomain {

	private static final long serialVersionUID = 3819038529105807227L;

	public SurveyLabel() {
	}

	private Long id;
	private Long sectionId;
	private String labelNumber;
	private Long surveyOrder;
	private String label;
	private boolean optional;
	private int globalPageNumber;
	private Long surveySectionId;
	private boolean complexityBand;
	private Long labelType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getLabelNumber() {
		return labelNumber;
	}

	public void setLabelNumber(String labelNumber) {
		this.labelNumber = labelNumber;
	}

	public Long getSurveyOrder() {
		return surveyOrder;
	}

	public void setSurveyOrder(Long surveyOrder) {
		this.surveyOrder = surveyOrder;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	public int getGlobalPageNumber() {
		return globalPageNumber;
	}

	public void setGlobalPageNumber(int globalPageNumber) {
		this.globalPageNumber = globalPageNumber;
	}

	public Long getSurveySectionId() {
		return surveySectionId;
	}

	public void setSurveySectionId(Long surveySectionId) {
		this.surveySectionId = surveySectionId;
	}

	public boolean isComplexityBand() {
		return complexityBand;
	}

	public void setComplexityBand(boolean complexityBand) {
		this.complexityBand = complexityBand;
	}

	public Long getLabelType() {
		return labelType;
	}

	public void setLabelType(Long labelType) {
		this.labelType = labelType;
	}
}
