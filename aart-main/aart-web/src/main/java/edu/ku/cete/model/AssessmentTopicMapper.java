package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.report.domain.AssessmentTopic;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public interface AssessmentTopicMapper {

	Integer insert(AssessmentTopic assessmentTopic);
	
	Integer update(AssessmentTopic assessmentTopic);
	
	void delete(@Param("contentAreaId") Long contentAreaId, @Param("schoolYear") Long schoolYear);

	List<AssessmentTopic> selectByTopicCode(@Param("schoolYear") Long schoolYear, @Param("testType") String testType,
			@Param("topicCode") String topicCode);

	List<String> getAvailableAssessmentCodes(@Param("schoolYear") Long schoolYear,
			@Param("contentAreaId") Long contentAreaId,@Param("gradeCourseId") Long gradeCourseId);

	List<AssessmentTopic> getTopicsByAssessmentCodes(@Param("assessmentCode") String assessmentCode);
	
}
