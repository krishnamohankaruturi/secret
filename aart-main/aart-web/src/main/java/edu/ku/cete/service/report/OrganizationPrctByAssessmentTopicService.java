package edu.ku.cete.service.report;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.ku.cete.report.domain.OrganizationPrctByAssessmentTopic;
import edu.ku.cete.report.domain.OrganizationPrctTopicReportsDTO;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
@Service
public interface OrganizationPrctByAssessmentTopicService {

	Integer insertOrUpdate(
			OrganizationPrctByAssessmentTopic orgPrctByAssessmentTopic);

	void deleteOrganizationPrct(Long stateId, Long contentAreaId, Long reportYear,
			Long testingProgramId, String reportCycle);

	List<Long> getSchoolIdsForReportGeneration(Long schoolYear, Long stateId,
			String assessmentCode, String reportCycle, Long testingProgramId,
			Long gradeCourseId, Long contentAreaId, int offset, int pageSize);

	List<OrganizationPrctTopicReportsDTO> getOrganizationDetails(Long schoolId,
			String reportCycle, Long testingProgramId, String assessmentCode,
			Long schoolYear, Long assessmentProgramId, List<Long> topicsId);

}
