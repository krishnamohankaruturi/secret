package edu.ku.cete.domain.student;

import java.util.ArrayList;
import java.util.List;

public class StudentSurveySectionsJson {
	private Long surveyId;
	private Long surveySectionId;
	private String surveySectionCode;
	private String parentSurveySectionCode;
	private Long parentSurvetSectionId;
	private List<SurveyPageJson> surevyPageJsons = new ArrayList<SurveyPageJson>();
			
	public Long getSurveySectionId() {
		return surveySectionId;
	}
	public void setSurveySectionId(Long surveySectionId) {
		this.surveySectionId = surveySectionId;
	}
	
	public String getSurveySectionCode() {
		return surveySectionCode;
	}
	
	public void setSurveySectionCode(String surveySectionCode) {
		this.surveySectionCode = surveySectionCode;
	}
	public List<SurveyPageJson> getSurevyPageJsons() {
		return surevyPageJsons;
	}	
	public void setSurevyPageJsons(List<SurveyPageJson> surevyPageJsons) {
		this.surevyPageJsons = surevyPageJsons;
	}
	public Long getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}
	public String getParentSurveySectionCode() {
		return parentSurveySectionCode;
	}
	public void setParentSurveySectionCode(String parentSurveySectionCode) {
		this.parentSurveySectionCode = parentSurveySectionCode;
	}
	public Long getParentSurvetSectionId() {
		return parentSurvetSectionId;
	}
	public void setParentSurvetSectionId(Long parentSurvetSectionId) {
		this.parentSurvetSectionId = parentSurvetSectionId;
	}	
}
