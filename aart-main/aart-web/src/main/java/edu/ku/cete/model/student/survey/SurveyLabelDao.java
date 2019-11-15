package edu.ku.cete.model.student.survey;

import java.util.List;

import edu.ku.cete.domain.student.survey.SurveyLabel;

public interface SurveyLabelDao {

	List<SurveyLabel> selectSurveyLabelsByAndCondition();

	List<Integer> getAllDependentPages(Integer globalPageNum);
}
