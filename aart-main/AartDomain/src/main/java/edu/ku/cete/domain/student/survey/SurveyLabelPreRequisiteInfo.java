/**
 * 
 */
package edu.ku.cete.domain.student.survey;

import java.util.ArrayList;
import java.util.List;


/**
 * @author mahesh
 *
 */
public class SurveyLabelPreRequisiteInfo {
	private Long surveyLabelId;
	
	private List<Long> surveyResponseIds = new ArrayList<Long>();
	
	private List<String> preRequisiteConditions = new ArrayList<String>();
	
	public static SurveyLabelPreRequisiteInfo getInstance(
			SurveyLabelPrerequisite surveyLabelPrerequisite) {
		SurveyLabelPreRequisiteInfo
		surveyLabelPreRequisiteInfo = new SurveyLabelPreRequisiteInfo();
		if(surveyLabelPrerequisite != null
				&& surveyLabelPrerequisite.getSurveyLabelId() != null) {
			surveyLabelPreRequisiteInfo.setSurveyLabelId(
					surveyLabelPrerequisite.getSurveyLabelId());
		}
		surveyLabelPreRequisiteInfo.addSurveyResponseId(
				surveyLabelPrerequisite);
		surveyLabelPreRequisiteInfo.addPreRequisiteCondition(
				surveyLabelPrerequisite);
		
		return surveyLabelPreRequisiteInfo;
	}

	/**
	 * @return the surveyLabelId
	 */
	public Long getSurveyLabelId() {
		return surveyLabelId;
	}

	/**
	 * @param surveyLabelId the surveyLabelId to set
	 */
	public void setSurveyLabelId(Long surveyLabelId) {
		this.surveyLabelId = surveyLabelId;
	}

	/**
	 * @return the surveyResponseIds
	 */
	public List<Long> getSurveyResponseIds() {
		return surveyResponseIds;
	}

	/**
	 * @param surveyResponseIds the surveyResponseIds to set
	 */
	public void setSurveyResponseIds(List<Long> surveyResponseIds) {
		this.surveyResponseIds = surveyResponseIds;
	}
	
	public void addSurveyResponseId(Long surveyResponseId) {
		if(surveyResponseId != null
				&& !surveyResponseIds.contains(surveyResponseId)) {
			surveyResponseIds.add(surveyResponseId);
		}
	}
	
	public void addSurveyResponseId(
			SurveyLabelPrerequisite surveyLabelPrerequisite) {
		if(surveyLabelPrerequisite != null
				&& surveyLabelPrerequisite.getSurveyResponseId() != null) {
			addSurveyResponseId(
					surveyLabelPrerequisite.getSurveyResponseId());
		}
	}

	/**
	 * @return the preRequisiteConditions
	 */
	public List<String> getPreRequisiteConditions() {
		return preRequisiteConditions;
	}

	/**
	 * @param preRequisiteConditions the preRequisiteConditions to set
	 */
	public void setPreRequisiteConditions(List<String> preRequisiteConditions) {
		this.preRequisiteConditions = preRequisiteConditions;
	}
	
	/**
	 * @param preRequisiteCondition
	 */
	public void addPreRequisiteCondition(String preRequisiteCondition) {
		if(preRequisiteCondition != null
				&& !preRequisiteConditions.contains(preRequisiteCondition)) {
			preRequisiteConditions.add(preRequisiteCondition);
		}
	}
	
	public void addPreRequisiteCondition(
			SurveyLabelPrerequisite surveyLabelPrerequisite) {
		if(surveyLabelPrerequisite != null
				&& surveyLabelPrerequisite.getPreRequisiteCondition() != null) {
			addPreRequisiteCondition(
					surveyLabelPrerequisite.getPreRequisiteCondition());
		}
	}
	
	@Override
	public String toString() {
		return "SurveyLabelPreRequisiteInfo [surveyLabelId=" + surveyLabelId
				+ ", surveyResponseIds=" + surveyResponseIds + "]";
	}

}
