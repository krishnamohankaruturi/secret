package edu.ku.cete.service;

import java.util.Date;
import java.util.List;

import edu.ku.cete.domain.interim.FeedbackQuestionAndResponse;
import edu.ku.cete.domain.interim.FeedbackResponse;

public interface InterimFeedbackService {

	List<FeedbackQuestionAndResponse> getFeedbackQuestionsAndResponses(Long assessmentProgramId, List<Long> testletIds,
			Long userId, Date startDate);

	List<Long> getTestletsByTest(Long testId);

	void saveFeedbackResponses(List<FeedbackResponse> responses);

	FeedbackResponse getResponse(Long responseId);
	
}