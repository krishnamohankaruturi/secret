package edu.ku.cete.report;

import java.util.List;

public class SubScoreMedianScore {
	
	private Long organizationId;
	private List<ScaleScoresBySubScoreDef> scaleScoresBySubScoreDef;
	
	/**
	 * @return the organizationId
	 */
	public Long getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * @return the scaleScoresBySubScoreDef
	 */
	public List<ScaleScoresBySubScoreDef> getScaleScoresBySubScoreDef() {
		return scaleScoresBySubScoreDef;
	}
	/**
	 * @param scaleScoresBySubScoreDef the scaleScoresBySubScoreDef to set
	 */
	public void setScaleScoresBySubScoreDef(
			List<ScaleScoresBySubScoreDef> scaleScoresBySubScoreDef) {
		this.scaleScoresBySubScoreDef = scaleScoresBySubScoreDef;
	}
	
}
