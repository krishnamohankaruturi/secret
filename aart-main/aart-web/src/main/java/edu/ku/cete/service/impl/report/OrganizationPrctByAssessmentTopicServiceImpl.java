package edu.ku.cete.service.impl.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.model.OrganizationPrctByAssessmentTopicMapper;
import edu.ku.cete.report.domain.OrganizationPrctByAssessmentTopic;
import edu.ku.cete.report.domain.OrganizationPrctTopicReportsDTO;
import edu.ku.cete.service.report.OrganizationPrctByAssessmentTopicService;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
@Service
public class OrganizationPrctByAssessmentTopicServiceImpl implements
		OrganizationPrctByAssessmentTopicService {

	@Autowired
	OrganizationPrctByAssessmentTopicMapper organizationPrctByAssessmentTopicMapper;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertOrUpdate(
			OrganizationPrctByAssessmentTopic orgPrctByAssessmentTopic) {
		
        int result = 0;		
		result = organizationPrctByAssessmentTopicMapper.update(orgPrctByAssessmentTopic);		
		if(result == 0)
			result = organizationPrctByAssessmentTopicMapper.insert(orgPrctByAssessmentTopic);		
		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteOrganizationPrct(Long stateId, Long contentAreaId,
			Long reportYear, Long testingProgramId, String reportCycle) {		
		organizationPrctByAssessmentTopicMapper.delete(contentAreaId, reportYear, stateId, testingProgramId, reportCycle);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getSchoolIdsForReportGeneration(Long schoolYear,
			Long stateId, String assessmentCode, String reportCycle,
			Long testingProgramId, Long gradeCourseId, Long contentAreaId,
			int offset, int pageSize) {
		return organizationPrctByAssessmentTopicMapper.getSchoolIdsForReportGeneration(schoolYear, stateId, assessmentCode,
				reportCycle, testingProgramId, gradeCourseId, contentAreaId, offset, pageSize);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<OrganizationPrctTopicReportsDTO> getOrganizationDetails(
			Long schoolId, String reportCycle, Long testingProgramId,
			String assessmentCode, Long schoolYear, Long assessmentProgramId,
			List<Long> topicsId) {
		return organizationPrctByAssessmentTopicMapper.getOrganizationDetails(schoolId, reportCycle, testingProgramId,
				assessmentCode, schoolYear, assessmentProgramId,
				topicsId);
	}


}
