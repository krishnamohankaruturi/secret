package edu.ku.cete.service.report;

import java.util.List;

import edu.ku.cete.report.domain.AssessmentTopic;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public interface AssessmentTopicService {
	Integer insertOrUpdateAssessmentTopic(AssessmentTopic assessmentTopic);

	void deleteAssessmentTopic(Long contentAreaId, Long schoolYear);

	List<AssessmentTopic> getAssessmentTopic(Long schoolYear, String testType,
			String topicCode);

	List<String> getAvailableAssessmentCodes(Long schoolYear,
			Long gradeCourseId, Long contentAreaId);

	List<AssessmentTopic> getTopicsByAssessmentCodes(String assessmentCode);
	
}
