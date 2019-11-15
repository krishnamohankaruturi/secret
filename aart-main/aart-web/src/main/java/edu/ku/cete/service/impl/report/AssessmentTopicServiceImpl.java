package edu.ku.cete.service.impl.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.model.AssessmentTopicMapper;
import edu.ku.cete.report.domain.AssessmentTopic;
import edu.ku.cete.service.report.AssessmentTopicService;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
@Service
public class AssessmentTopicServiceImpl implements
		AssessmentTopicService {
	
	@Autowired
	AssessmentTopicMapper assessmentTopicMapper;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertOrUpdateAssessmentTopic(AssessmentTopic assessmentTopic) {
		
		int result = assessmentTopicMapper.update(assessmentTopic);
		
		if(result == 0)
			result = assessmentTopicMapper.insert(assessmentTopic);
		
		return result;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAssessmentTopic(Long contentAreaId, Long schoolYear){
		assessmentTopicMapper.delete(contentAreaId, schoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AssessmentTopic> getAssessmentTopic(Long schoolYear,
			String testType, String topicCode) {
		return assessmentTopicMapper.selectByTopicCode(schoolYear, testType, topicCode);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> getAvailableAssessmentCodes(Long schoolYear,
			Long gradeCourseId, Long contentAreaId) {
		return assessmentTopicMapper.getAvailableAssessmentCodes(schoolYear,contentAreaId, gradeCourseId) ;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AssessmentTopic> getTopicsByAssessmentCodes(
			String assessmentCode) {
		
		return assessmentTopicMapper.getTopicsByAssessmentCodes(assessmentCode);
	}
}
