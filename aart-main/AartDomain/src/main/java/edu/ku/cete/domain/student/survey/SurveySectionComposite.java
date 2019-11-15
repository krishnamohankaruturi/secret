/**
 * 
 */
package edu.ku.cete.domain.student.survey;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;


/**
 * @author mahesh
 * This has the survey section plus its parent.
 */
public class SurveySectionComposite extends SurveySection {
	private List<SurveySection>
	childSurveySections = new ArrayList<SurveySection>();


	/**
	 * @return the childSurveySections
	 */
	public List<SurveySection> getChildSurveySections() {
		return childSurveySections;
	}


	/**
	 * @param childSurveySections the childSurveySections to set
	 */
	public void setChildSurveySections(List<SurveySection> childSurveySections) {
		this.childSurveySections = childSurveySections;
	}

	public void addChildSurveySection(SurveySectionComposite childSurveySection) {
		if(
				!childSurveySections.contains(childSurveySection)
				&& childSurveySection.getParentSurveySectionId() != null
				&& childSurveySection.getParentSurveySectionId().equals(getId())
						) {
			childSurveySections.add(childSurveySection);
		} 
	}

	public void addChildSurveySection(
			List<SurveySectionComposite> childSurveySections) {
		if(getParentSurveySectionId() == null
				&& childSurveySections != null
				&& CollectionUtils.isNotEmpty(childSurveySections)
				) {
			for(SurveySectionComposite childSurveySection:childSurveySections) {
				addChildSurveySection(childSurveySection);
			}
		}
	}
}
