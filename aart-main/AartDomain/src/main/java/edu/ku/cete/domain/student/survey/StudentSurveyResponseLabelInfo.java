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
public class StudentSurveyResponseLabelInfo extends StudentSurveyResponseLabel {

	/**
	 * isPreRequisite
	 */
	private boolean isPreRequisite = false;
	/**
	 * hasPreRequisite.
	 */
	private boolean hasPreRequisite = false;
	/**
	 * metPreRequisite.
	 */
	private boolean metPreRequisite = false;
	/**
	 * preRequisiteResponseLabelIds.
	 */
	private List<Long> preRequisiteResponseLabelIds = new ArrayList<Long>();

	private String labelType;

	/**
	 * @return the doDisplay
	 */
	public boolean isDoDisplay() {
		return (!hasPreRequisite || (hasPreRequisite && metPreRequisite));
	}

	/**
	 * @return the isPreRequisite
	 */
	public boolean isIsPreRequisite() {
		return isPreRequisite;
	}

	/**
	 * @param isPreRequisite
	 *            the isPreRequisite to set
	 */
	public void setIsPreRequisite(boolean isPreRequisite) {
		this.isPreRequisite = isPreRequisite;
	}

	/**
	 * @return the hasPreRequisite
	 */
	public boolean isHasPreRequisite() {
		return hasPreRequisite;
	}

	/**
	 * @param hasPreRequisite
	 *            the hasPreRequisite to set
	 */
	public void setHasPreRequisite(boolean hasPreRequisite) {
		this.hasPreRequisite = hasPreRequisite;
	}

	/**
	 * @return the metPreRequisite
	 */
	public boolean isMetPreRequisite() {
		return metPreRequisite;
	}

	/**
	 * @param metPreRequisite
	 *            the metPreRequisite to set
	 */
	public void setMetPreRequisite(boolean metPreRequisite) {
		this.metPreRequisite = metPreRequisite;
	}

	/**
	 * @return the preRequisiteResponseLabelIds
	 */
	public List<Long> getPreRequisiteResponseLabelIds() {
		return preRequisiteResponseLabelIds;
	}

	/**
	 * @param preRequisiteResponseLabelIds
	 *            the preRequisiteResponseLabelIds to set
	 */
	public void setPreRequisiteResponseLabelIds(List<Long> preRequisiteResponseLabelIds) {
		this.preRequisiteResponseLabelIds = preRequisiteResponseLabelIds;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

}
