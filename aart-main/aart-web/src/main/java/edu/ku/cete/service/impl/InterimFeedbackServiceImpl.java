package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.interim.FeedbackQuestionAndResponse;
import edu.ku.cete.domain.interim.FeedbackResponse;
import edu.ku.cete.model.InterimFeedbackDao;
import edu.ku.cete.service.InterimFeedbackService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class InterimFeedbackServiceImpl implements InterimFeedbackService {
	
	private Logger logger = LoggerFactory.getLogger(InterimFeedbackServiceImpl.class);
	
	@Autowired
	private InterimFeedbackDao interimFeedbackDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<FeedbackQuestionAndResponse> getFeedbackQuestionsAndResponses(Long assessmentProgramId, List<Long> testletExternalIds, Long userId, Date startDate) {
		logger.debug("getting feedback QandA for testlets: " + StringUtils.join(testletExternalIds, ',') + " for user: " + userId);
		if(CollectionUtils.isEmpty(testletExternalIds)) {
			return new ArrayList<FeedbackQuestionAndResponse>();
		}
		return interimFeedbackDao.getQandA(assessmentProgramId, testletExternalIds, userId, startDate);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Long> getTestletsByTest(Long testId) {
		logger.debug("getting testlet external ids for test: " + testId);
		return interimFeedbackDao.getTestletsByTest(testId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public FeedbackResponse getResponse(Long responseId) {
		return interimFeedbackDao.getResponse(responseId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void saveFeedbackResponses(List<FeedbackResponse> responses) {
		for(FeedbackResponse response: responses){
			FeedbackResponse old = null;
			if(response.getResponseId() != null) {
				old = this.getResponse(response.getResponseId());
			}
			if(old != null) {
				old.setResponse(response.getResponse());
				old.setResponseActive(response.getResponseActive());
				logger.debug("updating existing response: " + old.getResponseId());
				interimFeedbackDao.updateResponse(old);
			}else {
				logger.debug("inserting new response");
				interimFeedbackDao.insertResponse(response);
			}
		}
	}
	
	
}