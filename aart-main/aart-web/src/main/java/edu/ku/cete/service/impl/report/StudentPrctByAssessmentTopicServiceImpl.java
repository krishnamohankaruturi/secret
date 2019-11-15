package edu.ku.cete.service.impl.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.model.StudentPrctByAssessmentTopicMapper;
import edu.ku.cete.report.domain.StudentPrctByAssessmentTopic;
import edu.ku.cete.report.domain.StudentPrctTopicReportsDTO;
import edu.ku.cete.service.report.StudentPrctByAssessmentTopicService;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
@Service
public class StudentPrctByAssessmentTopicServiceImpl
		implements StudentPrctByAssessmentTopicService {
	
	@Autowired
	StudentPrctByAssessmentTopicMapper studentPrctByAssessmentTopicMapper;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertOrUpdate(
			StudentPrctByAssessmentTopic studentPrctByAssessmentTopic) {
		int result = 0;
		
		result = studentPrctByAssessmentTopicMapper.update(studentPrctByAssessmentTopic);		
		if(result == 0)
			result = studentPrctByAssessmentTopicMapper.insert(studentPrctByAssessmentTopic);		
		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteStudentPrct(Long stateId, Long contentAreaId,
			Long reportYear, Long testingProgramId, String reportCycle) {		
		studentPrctByAssessmentTopicMapper.delete(contentAreaId, reportYear, stateId, testingProgramId, reportCycle);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentPrctTopicReportsDTO> getStudentDetails(Long schoolId,
			String reportCycle, Long testingProgramId, String assessmentCode,
			Long schoolYear, Long assessmentProgramId, List<Long> topicsId) {
		
		return studentPrctByAssessmentTopicMapper.getStudentDetails(schoolId,
				reportCycle, testingProgramId, assessmentCode,
				schoolYear, assessmentProgramId, topicsId);
	}

}
