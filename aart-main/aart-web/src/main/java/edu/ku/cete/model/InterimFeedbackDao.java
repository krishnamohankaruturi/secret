package edu.ku.cete.model;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.interim.FeedbackQuestionAndResponse;
import edu.ku.cete.domain.interim.FeedbackResponse;

public interface InterimFeedbackDao{
	
	List<FeedbackQuestionAndResponse> getQandA(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("testletExternalIds") List<Long> testletExternalIds, @Param("userId") Long userId, @Param("startDate") Date startDate);
	
	FeedbackResponse getResponse(@Param("responseId") Long responseId);
	
	List<Long> getTestletsByTest(@Param("testId") Long testId);
	
	void insertResponse(FeedbackResponse response);
	
	void updateResponse(FeedbackResponse response);
	
}