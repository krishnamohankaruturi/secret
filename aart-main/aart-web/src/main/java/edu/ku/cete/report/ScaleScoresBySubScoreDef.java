package edu.ku.cete.report;

import java.util.List;

public class ScaleScoresBySubScoreDef {
	private String subScoreDefinitionName;
	private List<SubScaleScoresByStudent> subScaleScoresByStudent;
	
	
	/**
	 * @return the subScoreDefinitionName
	 */
	public String getSubScoreDefinitionName() {
		return subScoreDefinitionName;
	}
	/**
	 * @param subScoreDefinitionName the subScoreDefinitionName to set
	 */
	public void setSubScoreDefinitionName(String subScoreDefinitionName) {
		this.subScoreDefinitionName = subScoreDefinitionName;
	}
	/**
	 * @return the subScaleScoresByStudent
	 */
	public List<SubScaleScoresByStudent> getSubScaleScoresByStudent() {
		return subScaleScoresByStudent;
	}
	/**
	 * @param subScaleScoresByStudent the subScaleScoresByStudent to set
	 */
	public void setSubScaleScoresByStudent(
			List<SubScaleScoresByStudent> subScaleScoresByStudent) {
		this.subScaleScoresByStudent = subScaleScoresByStudent;
	}
	
	
}
